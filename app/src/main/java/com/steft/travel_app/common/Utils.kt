@file:Suppress("NestedLambdaShadowedImplicitParameter")

package com.steft.travel_app.common

import arrow.core.Either
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.*
import kotlin.collections.ArrayList

object Utils {
    suspend fun <A, B> Iterable<A>.pMap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    suspend fun <A, B> Array<A>.pMap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    fun String.toUUID(): UUID =
        UUID.fromString(this)

    fun <Y : Any, Z: Any, T : Either<Y, Z>> Iterable<T>.filterRight(): List<Z> =
        filterRightTo<Y, Z, T>(arrayListOf())


    private fun <Y : Any, Z: Any, T : Either<Y, Z>> Iterable<T>.filterRightTo(destination: ArrayList<Z>): List<Z> {
        forEach { it.map { destination.add(it) } }
        return destination
    }
}