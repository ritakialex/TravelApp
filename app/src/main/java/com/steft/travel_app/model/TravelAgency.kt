package com.steft.travel_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "travel_agency")
data class TravelAgency(@PrimaryKey override val id: UUID,
                        @ColumnInfo val name: Name,
                        @ColumnInfo val address: Address) : Model