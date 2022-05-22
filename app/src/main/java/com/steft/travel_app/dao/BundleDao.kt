package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.steft.travel_app.model.Bundle
import java.util.*

@Dao
interface BundleDao {
    @Query("SELECT * FROM bundle")
    suspend fun getAll(): List<Bundle>

    @Query("SELECT * FROM bundle WHERE travel_agency = :travelAgency")
    suspend fun getAll(travelAgency: UUID): List<Bundle>

    @Query("SELECT * FROM bundle WHERE location = :locationId")
    suspend fun findByLocation(locationId: UUID): List<Bundle>

    @Query("SELECT * FROM bundle WHERE id = :id")
    suspend fun findById(id: UUID): Bundle?

    @Insert
    suspend fun insertAll(vararg bundles: Bundle)

    @Update
    suspend fun update(bundle: Bundle)

    @Query("DELETE FROM bundle WHERE id = :id AND travel_agency = :travelAgency")
    suspend fun delete(id: UUID, travelAgency: UUID): Int
}
