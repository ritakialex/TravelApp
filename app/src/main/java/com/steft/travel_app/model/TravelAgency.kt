package com.steft.travel_app.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.steft.travel_app.common.Address
import com.steft.travel_app.common.Name
import com.steft.travel_app.common.Sha256
import com.steft.travel_app.common.Username
import java.util.UUID

@Entity(
    tableName = "travel_agency",
    indices = [Index(
        value = ["username"],
        unique = true)])
data class TravelAgency(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val name: Name,
    val address: Address,
    val username: Username,
    val password: Sha256)