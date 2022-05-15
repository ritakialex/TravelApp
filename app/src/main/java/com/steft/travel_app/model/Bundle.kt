package com.steft.travel_app.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.steft.travel_app.common.LocationType
import com.steft.travel_app.common.Name
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "bundle",
    foreignKeys = [
        ForeignKey(
            entity = TravelAgency::class,
            parentColumns = ["id"],
            childColumns = ["travel_agency"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["location"],
            onDelete = ForeignKey.RESTRICT
        )]
)
data class Bundle(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "travel_agency") val travelAgency: UUID,
    val location: UUID,
    val date: Date,
    val price: Double,
    val duration: Int,
    val hotels: List<Name>,
    val type: LocationType
)



