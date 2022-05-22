package com.steft.travel_app.model

import arrow.core.*
import arrow.typeclasses.Semigroup
import com.steft.travel_app.common.*
import java.util.UUID


data class CustomerDetails(
    val name: Name,
    val surname: Name,
    val phone: Phone,
    val email: Email,
    val hotel: String)

data class Registration(
    val bundle: UUID,
    val travelAgency: UUID,
    val customers: List<CustomerDetails>)

object RegistrationUtils {
    private val name = CustomerDetails::name.name
    private val surname = CustomerDetails::surname.name
    private val phone = CustomerDetails::phone.name
    private val email = CustomerDetails::email.name
    private val hotel = CustomerDetails::hotel.name

    fun customerDetailsToMap(customerDetails: CustomerDetails) =
        mapOf(
            name to customerDetails.name.toString(),
            surname to customerDetails.surname.toString(),
            phone to customerDetails.phone.toString(),
            email to customerDetails.email.toString(),
            hotel to customerDetails.hotel)


    fun mapToRegistration(
        regId: Any?,
        travelAgency: Any?,
        detailsList: List<Map<String, Any>>): Either<Throwable, Registration> {

        val mapToDetails: (Map<String, Any>) -> ValidatedObject<CustomerDetails> = { map ->
            Name.makeValidated(map[name].toString())
                .zip(
                    Semigroup.nonEmptyList(),
                    Name.makeValidated(map[surname] as String),
                    Phone.makeValidated(map[phone] as String),
                    Email.makeValidated(map[email] as String))
                { name, surname, phone, email ->
                    CustomerDetails(name, surname, phone, email, map[hotel] as String)
                }
        }

        val makeRegistration: (ValidatedObject<List<CustomerDetails>>) -> ValidatedObject<Registration> =
            { validatedCustomerDetails ->
                val validatedRegId =
                    Validated.catch { UUID.fromString(regId as String)!! }
                        .mapLeft { ValidationError(it.toString()).nel() }

                val validatedAgencyId =
                    Validated.catch { UUID.fromString(travelAgency as String)!! }
                        .mapLeft { ValidationError(it.toString()).nel() }

                validatedCustomerDetails
                    .zip(Semigroup.nonEmptyList(), validatedAgencyId, validatedRegId) { details, agencyId, bundleId ->
                        Registration(bundleId, agencyId, details)
                    }
            }

        val foldErrors: (NonEmptyList<ValidationError>) -> InvalidObjectException = { errs ->
            InvalidObjectException(ValidateUtils.foldValidationErrors(errs))
        }

        return Either.catch {
            detailsList
                .map(mapToDetails)
                .traverseValidated(::identity)
                .let(makeRegistration)
                .toEither()
                .mapLeft(foldErrors)
        }.flatten()
    }
}
