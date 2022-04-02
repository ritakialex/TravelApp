package com.steft.travel_app.model

import java.util.UUID


data class CustomerDetails(val name: Name,
                           val surname: Name,
                           val phone: Phone,
                           val email: Email,
                           val hotel: String)

data class Registration(val bundle: UUID,
                        val customers: List<CustomerDetails>)
