package com.steft.travel_app.dao

import java.util.*

interface ModelDao<T> {
    fun getAll(): List<T>

    fun findById(id: UUID): T

    fun insertAll(vararg agencies: T)

    fun delete(id: UUID)
}