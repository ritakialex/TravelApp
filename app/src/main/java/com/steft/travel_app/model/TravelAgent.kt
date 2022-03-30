package com.steft.travel_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(foreignKeys = [
    ForeignKey(entity = TravelAgency::class,
               parentColumns = ["id"],
               childColumns = ["travel_agency"],
               onDelete = ForeignKey.RESTRICT),
    ForeignKey(entity = Bundle::class,
               parentColumns = ["id"],
               childColumns = ["bundle"],
               onDelete = ForeignKey.RESTRICT)])
data class TravelAgent(@PrimaryKey override val id: UUID,
                       val name: Name,
                       val surname: Name,
                       val address: Address,
                       val gender: Gender,
                       @ColumnInfo(name = "date_of_birth") val dateOfBirth: Date,
                       val specialty: Specialty,
                       @ColumnInfo(name = "travel_agency") val travelAgency: UUID,
                       val bundle: UUID) : Model