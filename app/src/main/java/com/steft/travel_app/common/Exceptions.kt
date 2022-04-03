package com.steft.travel_app.common

private fun derivedExceptionMessage(exception: Throwable) = "Original exception $exception"

class InvalidObjectException(override val message: String) : Exception(message) {
    companion object {
        fun fromOtherException(exception: Throwable) =
            InvalidObjectException(derivedExceptionMessage(exception))
    }
}

class CorruptDatabaseObjectException(override val message: String) : Exception(message) {
    companion object {
        fun fromOtherException(exception: Throwable) =
            InvalidObjectException(derivedExceptionMessage(exception))
    }
}



