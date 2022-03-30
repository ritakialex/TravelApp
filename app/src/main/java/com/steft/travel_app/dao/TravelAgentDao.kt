package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.TravelAgent
import java.util.*


@Dao
interface TravelAgentDao : ModelDao<TravelAgent> {
    @Query("SELECT * FROM travel_agency")
    override fun getAll(): List<TravelAgent>

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    override fun findById(id: UUID): TravelAgent

    @Insert
    override fun insertAll(vararg agencies: TravelAgent)

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    override fun delete(id: UUID)
}