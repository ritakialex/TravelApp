@file:Suppress("NAME_SHADOWING")

package com.steft.travel_app.dao

import android.util.Log
import arrow.core.*
import arrow.core.computations.ResultEffect.bind
import arrow.typeclasses.Semigroup
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.steft.travel_app.common.Utils.pMap
import com.steft.travel_app.common.ValidateUtils
import com.steft.travel_app.common.ValidationError
import com.steft.travel_app.dao.DocumentRegistrationUtils.toMap
import com.steft.travel_app.dao.DocumentRegistrationUtils.toRegistration
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
    private val name = CustomerDetails::name.name
    private val surname = CustomerDetails::surname.name
    private val phone = CustomerDetails::phone.name
    private val email = CustomerDetails::email.name
    private val hotel = CustomerDetails::hotel.name

    fun toMap(customerDetails: CustomerDetails) =
        mapOf(name to customerDetails.name.toString(),
              surname to customerDetails.surname.toString(),
              phone to customerDetails.phone.toString(),
              email to customerDetails.email.toString(),
              hotel to customerDetails.hotel)

    fun toRegistration(id: Any?, detailsList: List<Map<String, Any>>) =
        Either.catch {
            detailsList.map { map ->
                Name.makeValidated(map[name].toString())
                    .zip(Semigroup.nonEmptyList(),
                         Name.makeValidated(map[surname] as String),
                         Phone.makeValidated(map[phone] as String),
                         Email.makeValidated(map[email] as String))
                    { name, surname, phone, email ->
                        CustomerDetails(name, surname, phone, email, map[hotel] as String)
                    }
            }.traverseValidated {
                it
            }.zip(Semigroup.nonEmptyList(),
                  Validated.catch { UUID.fromString(id as String)!! }
                      .mapLeft { ValidationError(it.toString()).nel() }) { details, id ->
                Registration(id, details)
            }.toEither()
        }.mapLeft {
            CorruptDatabaseObjectException(it.toString())
        }.flatMap {
            it.mapLeft { errs ->
                CorruptDatabaseObjectException(ValidateUtils.foldValidationErrors(errs))
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

    suspend fun getAll() = collection.get()
        .await()
        .pMap { document ->
            val bundleId = document.get("bundleId")

            val customerDetails = document.reference
                .collection("customers")
                .get()
                .await()
                .map { document ->
                    document.data
                }

            toRegistration(bundleId, customerDetails)
                .mapLeft {
                    Log.e("Firebase",
                          "Couldn't retrieve object with id $bundleId because of $it")
                }.orNull()

        }.filterNotNull()
}

