package com.steft.travel_app.validate

import arrow.core.Validated
import arrow.core.ValidatedNel

data class ValidationError(val message: String)

typealias ValidatedObject<T> = Validated<ValidationError, T>

object Validate {
}