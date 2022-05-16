package com.steft.travel_app.dto

import java.util.Date
import java.util.UUID

data class BundleDto(
    val id: UUID,
    val travelAgency: UUID,
    val location: UUID,
    val date: Date,
    val price: Double,
    val duration: Int,
    val hotels: List<String>,
    val type: String
)
