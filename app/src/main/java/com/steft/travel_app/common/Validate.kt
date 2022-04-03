package com.steft.travel_app.common

import arrow.core.NonEmptyList
import arrow.core.ValidatedNel

data class ValidationError(val message: String)

typealias ValidatedObject<T> = ValidatedNel<ValidationError, T>

object ValidateUtils {
    fun foldValidationErrors(errors: NonEmptyList<ValidationError>) =
        errors.foldLeft("") { acc, err -> "$acc\n${err.message}" }
}

class InvalidObjectException(override val message: String) : Exception()
