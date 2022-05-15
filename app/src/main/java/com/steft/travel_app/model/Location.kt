package com.steft.travel_app.model

import androidx.room.*
import com.steft.travel_app.common.LocationType
import com.steft.travel_app.common.Name
import java.util.*

@Entity(tableName = "location", indices = [Index(value = ["city", "country"])])
data class Location(
    @PrimaryKey val id: UUID,
    val city: Name,
    val country: Name
)
