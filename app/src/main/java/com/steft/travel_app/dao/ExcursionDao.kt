package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.Excursion
import java.util.*

@Dao
interface ExcursionDao {
    @Query("SELECT * FROM excursion")
    suspend fun getAll(): List<Excursion>

    @Query("SELECT * FROM excursion WHERE id = :id")
    suspend fun findById(id: UUID): Excursion

    @Insert
    suspend fun insertAll(vararg excursion: Excursion)

    @Query("DELETE FROM excursion WHERE id = :id")
    suspend fun delete(id: UUID)
}