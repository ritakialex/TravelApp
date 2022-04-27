package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.Location
import java.util.*

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun findById(id: UUID): Location

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        """
    )
    suspend fun search(query: String): List<Location>

    @Insert
    suspend fun insertAll(vararg location: Location)

    @Query("DELETE FROM location WHERE id = :id")
    suspend fun delete(id: UUID)
}