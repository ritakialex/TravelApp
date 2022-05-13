package com.steft.travel_app.dataTestRita

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user:User)

    @Query("Select * from user_table order by id Asc")
    fun readAllData(): LiveData<List<User>>




}