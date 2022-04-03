package com.steft.travel_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.steft.travel_app.common.ExcursionType
import com.steft.travel_app.common.Name
import java.util.*


@Entity(tableName = "excursion")
data class Excursion(@PrimaryKey val id: UUID,
                     val city: Name,
                     val country: Name,
                     val duration: Int,
                     val type: ExcursionType)
