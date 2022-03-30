package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.TravelAgent
import java.util.*


@Dao
interface TravelAgentDao {
    @Query("SELECT * FROM travel_agent")
    suspend fun getAll(): List<TravelAgent>

    @Query("SELECT * FROM travel_agent WHERE id = :id")
    suspend fun findById(id: UUID): TravelAgent

    @Insert
    suspend fun insertAll(vararg agents: TravelAgent)

    @Query("SELECT * FROM travel_agent WHERE id = :id")
    suspend fun delete(id: UUID)
}