package com.steft.travel_app.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date
import java.util.UUID

@Entity(tableName = "bundle")
data class Bundle(@PrimaryKey val id: UUID,
                  val city: Name,
                  val country: Name,
                  val price: Double,
                  val duration: Int, /* days */
                  @ColumnInfo(name = "starting_time") val startingTime: Date)