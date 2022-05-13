package com.steft.travel_app.dataTestRita

import androidx.lifecycle.LiveData

//a repository δινει προσβαση σε πολλα data sources
//είναι suggested best practice

class UserRepository (private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}