package com.steft.travel_app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.steft.travel_app.model.Bundle
import java.util.*

@Dao
interface BundleDao : ModelDao<Bundle> {
    @Query("SELECT * FROM travel_agency")
    override fun getAll(): List<Bundle>

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    override fun findById(id: UUID): Bundle

    @Insert
    override fun insertAll(vararg agencies: Bundle)

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    override fun delete(id: UUID)
}