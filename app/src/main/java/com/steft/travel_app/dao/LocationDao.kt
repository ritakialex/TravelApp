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
    suspend fun findAllOfAgency(travelAgency: UUID): List<Location>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun findById(id: UUID): Location?

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
    suspend fun insertAll(vararg locations: Location)

    @Query("DELETE FROM location WHERE id = :id AND travel_agency = :travelAgencyId")
    suspend fun deleteCustom(id: UUID, travelAgencyId: UUID): Int


    @Query(
        """
        SELECT * FROM location WHERE id = (
            SELECT location FROM bundle WHERE id = :bundleId
        )
        """)
    suspend fun findFromBundleId(bundleId: UUID): Location?
}