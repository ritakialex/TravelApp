@file:Suppress("NAME_SHADOWING", "NestedLambdaShadowedImplicitParameter")

package com.steft.travel_app.dao

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.steft.travel_app.common.CorruptDatabaseObjectException
import com.steft.travel_app.common.LogTag
import com.steft.travel_app.common.Utils.filterRight
import com.steft.travel_app.common.Utils.pMap
import com.steft.travel_app.dao.firebaseDb
import com.steft.travel_app.model.*
import kotlinx.coroutines.tasks.await
import java.util.*

interface RegistrationDao {
    suspend fun getAll(bundle: UUID): List<Registration>
    suspend fun insert(vararg registration: Registration)
    suspend fun register(bundle: UUID, vararg customers: CustomerDetails)
    suspend fun findByAgencyId(travelAgency: UUID): List<Registration>
    suspend fun registrationsExist(bundle: UUID): Boolean
}

private class RegistrationDaoImpl(private val db: FirebaseFirestore) : RegistrationDao {
    private val collection = db.collection("registration")

    override suspend fun insert(vararg registration: Registration) =
        registration.pMap { (bundle, travelAgency, customers) ->
            db.runBatch { batch ->
                val newRef = collection.document()
                val b = batch.set(
                    newRef,
                    mapOf(
                        "bundleId" to bundle.toString(),
                        "travelAgencyId" to travelAgency.toString()))

                customers.forEach {
                    b.set(
                        newRef.collection("customers").document(),
                        RegistrationUtils.customerDetailsToMap(it))
                }
            }.await()

            Unit //Would return Void!
        }.fold(Unit) { _, _ -> }

    override suspend fun registrationsExist(bundle: UUID) =
        collection
            .whereEqualTo("bundleId", bundle.toString())
            .get()
            .await()
            .documents
            .let {
                val initSize = it.size
                it.takeWhile { elem ->
                    elem.reference
                        .collection("customers")
                        .get()
                        .await()
                        .let { it.size() == 0 }
                }.let { it.size != initSize }
            }


    override suspend fun getAll(bundle: UUID) =
        collection
            .whereEqualTo("bundleId", bundle.toString())
            .get()
            .await()
            .pMap { document ->
                val bundleId = document.get("bundleId")
                val agencyId = document.get("travelAgencyId")

                val customerDetails = document.reference
                    .collection("customers")
                    .get()
                    .await()
                    .map { it.data }

                RegistrationUtils
                    .mapToRegistration(bundleId, agencyId, customerDetails)
                    .mapLeft(CorruptDatabaseObjectException::fromOtherException)
                    .tapLeft { err ->
                        Log.e(
                            LogTag.Firebase.tag,
                            "Couldn't retrieve object with id $bundleId because of $err")
                    }

            }.filterRight()

    override suspend fun findByAgencyId(travelAgency: UUID) =
        collection
            .whereEqualTo("travelAgencyId", travelAgency.toString())
            .get()
            .await()
            .pMap { document ->
                val bundleId = document.get("bundleId")
                val agencyId = document.get("travelAgencyId")

                val customerDetails = document.reference
                    .collection("customers")
                    .get()
                    .await()
                    .map { it.data }

                RegistrationUtils
                    .mapToRegistration(bundleId, agencyId, customerDetails)
                    .mapLeft(CorruptDatabaseObjectException::fromOtherException)
                    .tapLeft { err ->
                        Log.e(
                            LogTag.Firebase.tag,
                            "Couldn't retrieve object with id $bundleId because of $err")
                    }

            }.filterRight()

    override suspend fun register(bundle: UUID, vararg customers: CustomerDetails): Unit =
        collection
            .whereEqualTo("bundleId", bundle.toString())
            .get()
            .await()
            .single()
            .let { reg ->
                val ref = reg.reference
                db.runBatch { batch ->
                    customers.forEach {
                        batch.set(
                            ref.collection("customers").document(),
                            RegistrationUtils.customerDetailsToMap(it))
                    }
                }
                    .await()
                    .let { } //Converts java Void! to kotlin Unit
            }

}

fun FirebaseFirestore.registrationDao(): RegistrationDao =
    RegistrationDaoImpl(this)