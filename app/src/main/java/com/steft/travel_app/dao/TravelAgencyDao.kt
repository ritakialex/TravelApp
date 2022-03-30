package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.TravelAgency
import java.util.UUID

@Dao
interface TravelAgencyDao : ModelDao<TravelAgency> {
    @Query("SELECT * FROM travel_agency")
    override fun getAll(): List<TravelAgency>

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    override fun findById(id: UUID): TravelAgency

    @Insert
    override fun insertAll(vararg agencies: TravelAgency)

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    override fun delete(id: UUID)
}