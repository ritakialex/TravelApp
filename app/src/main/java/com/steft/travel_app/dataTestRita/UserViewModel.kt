package com.steft.travel_app.dataTestRita

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//o ρόλος του είναι να δίνει στο UI δεδομένα και να επιβιώνει στις αλλαγές
//λειτουργεί σαν communcator μεταξυ repository και UI

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData

    }

    fun addUser(user: User){
        viewModelScope.launch (Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}