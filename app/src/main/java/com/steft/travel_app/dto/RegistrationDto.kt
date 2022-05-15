package com.steft.travel_app.dto

import java.util.UUID

data class CustomerDetailsDto(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val hotel: String
)

data class RegistrationDto(
    val bundle: UUID,
    val customers: List<CustomerDetailsDto>
)
