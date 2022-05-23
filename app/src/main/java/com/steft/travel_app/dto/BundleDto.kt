package com.steft.travel_app.dto

import java.util.Date
import java.util.StringJoiner
import java.util.UUID

data class BundleDto(
    val id: UUID,
    val travelAgency: String,
    val location: UUID,
    val date: String,
    val price: Double,
    val duration: Int,
    val hotels: List<String>,
    val type: String
)
