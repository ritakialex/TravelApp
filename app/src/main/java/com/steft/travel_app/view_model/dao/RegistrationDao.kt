@file:Suppress("NAME_SHADOWING")

package com.steft.travel_app.view_model.dao

import android.util.Log
import com.steft.travel_app.common.CorruptDatabaseObjectException
import com.steft.travel_app.common.LogTag
import com.steft.travel_app.common.Utils.filterRight
import com.steft.travel_app.common.Utils.pMap
import com.steft.travel_app.model.*
import kotlinx.coroutines.tasks.await
import java.util.*

interface RegistrationDao {
    suspend fun getAll(): List<Registration>

    suspend fun findById(id: UUID): Registration

    suspend fun insertAll(vararg registration: Registration)

    suspend fun delete(id: UUID)
}

class RegistrationDaoImpl {
    private val db = firebaseDb()
    private val collection = db.collection("registration")

    suspend fun insert(vararg registration: Registration) =
        registration.pMap { (bundle, customers) ->
            db.runBatch { batch ->
                val newRef = collection.document()
                val b = batch.set(newRef, mapOf("bundleId" to bundle.toString()))

                customers.forEach {
                    b.set(newRef.collection("customers").document(),
                          RegistrationUtils.registrationToMap(it))
                }
            }.await()

            Unit //Would return Void!
        }

    suspend fun getAll() = collection.get()
        .await()
        .pMap { document ->
            val bundleId = document.get("bundleId")

            val customerDetails = document.reference
                .collection("customers")
                .get()
                .await()
                .map { it.data }

            RegistrationUtils
                .mapToRegistration(bundleId, customerDetails)
                .mapLeft(CorruptDatabaseObjectException::fromOtherException)
                .tapLeft { err ->
                    Log.e(LogTag.Firebase.tag,
                          "Couldn't retrieve object with id $bundleId because of $err")
                }

        }.filterRight()
}

