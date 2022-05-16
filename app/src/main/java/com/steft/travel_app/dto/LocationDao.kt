package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.CustomLocation
import com.steft.travel_app.model.Location
import java.util.*

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>

    @Query("SELECT * FROM custom_location")
    suspend fun getAllCustom(): List<CustomLocation>

    @Query("SELECT * FROM custom_location WHERE travel_agency = :travelAgencyId")
    suspend fun getAllCustom(travelAgencyId: UUID): List<CustomLocation>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun findById(id: UUID): Location?

    @Query("SELECT * FROM custom_location WHERE id = :id")
    suspend fun findByIdCustom(id: UUID): CustomLocation?

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        """
    )
    suspend fun search(query: String): List<Location>

    @Query(
        """
        SELECT * FROM custom_location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        AND travel_agency = :travelAgencyId
        """
    )
    suspend fun searchCustom(query: String, travelAgencyId: UUID): List<CustomLocation>

    @Insert
    suspend fun insertAll(vararg location: Location)

    @Insert
    suspend fun insertAllCustom(vararg location: CustomLocation)

    @Query("DELETE FROM location WHERE id = :id")
    suspend fun delete(id: UUID)

    @Query("DELETE FROM custom_location WHERE id = :id AND travel_agency = :travelAgencyId")
    suspend fun deleteCustom(id: UUID, travelAgencyId: UUID)
}