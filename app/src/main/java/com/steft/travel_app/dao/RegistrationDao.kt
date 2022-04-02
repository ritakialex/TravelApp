@file:Suppress("NAME_SHADOWING")

package com.steft.travel_app.dao

import android.util.Log
import arrow.core.Either
import arrow.core.computations.ResultEffect.bind
import arrow.core.flatMap
import arrow.core.orNull
import arrow.core.zip
import arrow.typeclasses.Semigroup
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.steft.travel_app.common.Utils.pMap
import com.steft.travel_app.common.ValidateUtils
import com.steft.travel_app.dao.DocumentRegistrationUtils.toCustomerDetails
import com.steft.travel_app.dao.DocumentRegistrationUtils.toMap
import com.steft.travel_app.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.util.*

interface RegistrationDao {
    suspend fun getAll(): List<Registration>

    suspend fun findById(id: UUID): Registration

    suspend fun insert(registration: Registration)

    suspend fun delete(id: UUID)
}

object DocumentRegistrationUtils {
    val name = CustomerDetails::name.name
    val surname = CustomerDetails::surname.name
    val phone = CustomerDetails::phone.name
    val email = CustomerDetails::email.name
    val hotel = CustomerDetails::hotel.name

    fun toMap(customerDetails: CustomerDetails) =
        mapOf(name to customerDetails.name.toString(),
              surname to customerDetails.surname.toString(),
              phone to customerDetails.phone.toString(),
              email to customerDetails.email.toString(),
              hotel to customerDetails.hotel)

    fun toCustomerDetails(map: Map<String, Any>) =
        Either.catch {
            Name.makeValidated(map[name].toString())
                .zip(Semigroup.nonEmptyList(),
                     Name.makeValidated(map[surname] as String),
                     Phone.makeValidated(map[phone] as String),
                     Email.makeValidated(map[email] as String))
                { name, surname, phone, email ->
                    CustomerDetails(name, surname, phone, email, map[hotel] as String)
                }
        }.flatMap {
            it.toEither()
                .mapLeft { errors ->
                    CorruptDatabaseObjectException(ValidateUtils.foldValidationErrors(errors))
                }
        }
}

class RegistrationDaoImpl {
    private val db = firebaseDb()
    private val collection = db.collection("registration")

    suspend fun insert(registration: Registration) = registration.let { (bundle, customers) ->
        db.runBatch { batch ->
            val newRef = collection.document()
            customers.forEach {
                batch.set(newRef, mapOf("bundleId" to bundle.toString()))
                batch.set(newRef
                              .collection("customers")
                              .document(), toMap(it))
            }
        }
    }.let {
        it.await(); Unit
    }

    suspend fun getAll() =
        collection.get()
            .await()
            .pMap { document ->
                val bundleId = document.get("bundleId")
                Either.catch {
                    UUID.fromString(bundleId.toString())
                }.mapLeft {
                    CorruptDatabaseObjectException("'$bundleId' is not a valid UUID")
                }.tapLeft {
                    Log.e("Firebase",
                          "Couldn't get registration from database because of: $it")
                }.map { id ->
                    val customerDetails = document.reference
                        .collection("customers")
                        .get()
                        .await()
                        .pMap { document ->
                            document.data
                                .let(::toCustomerDetails)
                                .tapLeft { err ->
                                    Log.e("Firebase",
                                          "Document ${document.data} couldn't be coerced to CustomerDetails because of $err")
                                }.orNull()
                        }.filterNotNull()

                    Registration(id, customerDetails)
                }.orNull()
            }.filterNotNull()
}
