package com.steft.travel_app.model

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey
import com.steft.travel_app.common.LocationType
import com.steft.travel_app.common.Name
import java.util.*

@Entity(tableName = "location", indices = [Index(value = ["city", "country"])])
data class Location(@PrimaryKey val id: UUID,
                     val city: Name,
                     val country: Name)
