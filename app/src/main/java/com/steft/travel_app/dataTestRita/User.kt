package com.steft.travel_app.dataTestRita;

import androidx.room.Entity;
import androidx.room.PrimaryKey

@Entity(tableName ="user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int
)
