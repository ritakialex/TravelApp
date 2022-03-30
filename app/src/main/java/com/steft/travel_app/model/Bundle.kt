package com.steft.travel_app.model

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.Date
import java.util.UUID

@Entity
data class Bundle(@PrimaryKey override val id: UUID,
                  @ColumnInfo val city: Name,
                  @ColumnInfo val country: Name,
                  @ColumnInfo val price: Double,
                  @ColumnInfo val duration: Int, /* days */
                  @ColumnInfo val starting_time: Date) : Model