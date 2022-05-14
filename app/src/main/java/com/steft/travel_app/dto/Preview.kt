package com.steft.travel_app.dto

import java.util.UUID

interface Preview {
    val id: UUID
    val title: String
    val details: String
}