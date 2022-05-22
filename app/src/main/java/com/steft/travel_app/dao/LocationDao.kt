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

    @Query("SELECT * FROM location WHERE travel_agency = :travelAgency OR travel_agency IS NULL")
    suspend fun getAllOfAgency(travelAgency: UUID): List<Location>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun findByIdAll(id: UUID): Location?

    @Query("SELECT * FROM location WHERE id = :id AND travel_agency = :travelAgency")
    suspend fun findByOfAgency(id: UUID, travelAgency: UUID): Location?

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        AND travel_agency = :travelAgency
        OR travel_agency IS NULL
        """
    )
    suspend fun searchOfAgency(query: String, travelAgency: UUID): List<Location>

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        """
    )
    suspend fun searchAll(query: String): List<Location>

    @Insert
    suspend fun insert(vararg location: Location)

    @Query("DELETE FROM location WHERE id = :id AND travel_agency = :travelAgencyId")
    suspend fun deleteCustom(id: UUID, travelAgencyId: UUID): Int
}