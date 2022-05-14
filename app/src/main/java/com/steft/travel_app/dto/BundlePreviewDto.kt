package com.steft.travel_app.dto

import java.util.*

data class BundlePreviewDto(
    override val id: UUID,
    override val title: String,
    override val details: String
) : Preview
