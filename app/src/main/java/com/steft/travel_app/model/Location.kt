package com.steft.travel_app.model

import androidx.room.*
import com.steft.travel_app.common.LocationType
import com.steft.travel_app.common.Name
import java.util.*

@Entity(
    tableName = "location",
    indices = [Index(value = ["city", "country"])],
    foreignKeys = [
        ForeignKey(
            entity = TravelAgency::class,
            parentColumns = ["id"],
            childColumns = ["travel_agency"],
            onDelete = ForeignKey.RESTRICT)])
data class Location(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "travel_agency") val travelAgency: UUID?,
    val city: Name,
    val country: Name
)
