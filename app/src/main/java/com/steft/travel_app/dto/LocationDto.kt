package com.steft.travel_app.dto

import java.util.UUID

data class LocationDto(
    val id: UUID,
    val travelAgency: UUID?,
    val city: String,
    val country: String
)
