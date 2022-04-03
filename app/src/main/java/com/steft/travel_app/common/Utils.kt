package com.steft.travel_app.common

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*

object Utils {
    suspend fun <A, B> Iterable<A>.pMap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    fun String.toUUID(): UUID =
        UUID.fromString(this)
}