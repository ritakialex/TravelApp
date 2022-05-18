package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.Bundle
import java.util.*

@Dao
interface BundleDao {
    @Query("SELECT * FROM bundle")
    suspend fun getAll(): List<Bundle>

    @Query("SELECT * FROM bundle WHERE travel_agency = :travelAgency")
    suspend fun getAll(travelAgency: UUID): List<Bundle>

    @Query("SELECT * FROM bundle WHERE id = :id")
    suspend fun findById(id: UUID): Bundle?

    @Insert
    suspend fun insertAll(vararg bundles: Bundle)

    @Query("DELETE FROM bundle WHERE id = :id")
    suspend fun delete(id: UUID)
}