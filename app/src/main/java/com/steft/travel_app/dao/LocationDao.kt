package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.Location
import java.util.*

@Dao
interface LocationDao {
    @Query("SELECT * FROM location WHERE travelAgency IS NULL")
    suspend fun getAll(): List<Location>

    @Query("SELECT * FROM location WHERE travelAgency = :travelAgencyId")
    suspend fun getAllCustom(travelAgencyId: UUID): List<Location>

    @Query("SELECT * FROM location WHERE id = :id AND travelAgency IS NULL")
    suspend fun findById(id: UUID): Location

    @Query("SELECT * FROM location WHERE id = :id AND travelAgency = :travelAgencyId")
    suspend fun findByIdCustom(id: UUID, travelAgencyId: UUID)

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        AND travelAgency IS NULL
        """
    )
    suspend fun search(query: String): List<Location>

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        AND travelAgency = :travelAgencyId
        """
    )
    suspend fun searchCustom(query: String, travelAgencyId: UUID): List<Location>

    @Insert
    suspend fun insertAll(vararg location: Location)

    @Query("DELETE FROM location WHERE id = :id AND travelAgency IS NULL")
    suspend fun delete(id: UUID)

    @Query("DELETE FROM location WHERE id = :id AND travelAgency = :travelAgencyId")
    suspend fun deleteCustom(id: UUID, travelAgencyId: UUID)
}