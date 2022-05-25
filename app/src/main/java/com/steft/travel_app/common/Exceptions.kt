package com.steft.travel_app.common

private fun derivedExceptionMessage(exception: Throwable) = "Original exception $exception"


data class InvalidObjectException(override val message: String) : Exception(message) {
    companion object {
        fun fromOtherException(exception: Throwable) =
            InvalidObjectException(derivedExceptionMessage(exception))
    }
}

data class CorruptDatabaseObjectException(override val message: String) : Exception(message) {
    companion object {
        fun fromOtherException(exception: Throwable) =
            CorruptDatabaseObjectException(derivedExceptionMessage(exception))
    }
}

data class UnauthorizedException(override val message: String) : Exception(message) {
    companion object {
        fun fromOtherException(exception: Throwable) =
            UnauthorizedException(derivedExceptionMessage(exception))
    }
}



