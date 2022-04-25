package com.steft.travel_app.dto

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.TravelAgency
import java.util.UUID

@Dao
interface TravelAgencyDao {
    @Query("SELECT * FROM travel_agency")
    suspend fun getAll(): List<TravelAgency>

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    suspend fun findById(id: UUID): TravelAgency

    @Insert
    suspend fun insertAll(vararg agencies: TravelAgency)

    @Query("DELETE FROM travel_agency WHERE id = :id")
    suspend fun delete(id: UUID)
}