package com.steft.travel_app.dto

import com.steft.travel_app.model.TravelAgency
import java.util.UUID

data class LocationDto(
    val id: UUID,
    val travelAgency: TravelAgency?,
    val city: String,
    val country: String
)
