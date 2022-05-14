package com.steft.travel_app.dto

import java.util.UUID

data class LocationPreviewDto(
    override val id: UUID,
    override val title: String,
    override val details: String
) : Preview
