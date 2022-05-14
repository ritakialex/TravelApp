package com.steft.travel_app.dto

import java.util.UUID

data class TravelAgencyDto(
    val id: UUID,
    val name: String,
    val address: String,
    val username: String
)
