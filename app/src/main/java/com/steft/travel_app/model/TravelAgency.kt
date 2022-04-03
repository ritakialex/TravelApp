package com.steft.travel_app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.steft.travel_app.common.Address
import com.steft.travel_app.common.Name
import java.util.UUID

@Entity(tableName = "travel_agency")
data class TravelAgency(@PrimaryKey val id: UUID,
                        val name: Name,
                        val address: Address)