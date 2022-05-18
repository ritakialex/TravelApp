@file:Suppress("SimpleRedundantLet")

package com.steft.travel_app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import arrow.core.*
import arrow.typeclasses.Semigroup
import com.steft.travel_app.common.*
import com.steft.travel_app.dao.AppDatabase
import com.steft.travel_app.model.TravelAgency
import kotlinx.coroutines.launch


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val agencyDao = database.travelAgencyDao()

    fun login(username: String, password: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        viewModelScope.launch {
            agencyDao
                .getPassword(Username(username))
                .let { Either.catch { it.string to Sha256.split(it).second } }
                .map { (storedEntirePass, storedSalt) ->
                    Sha256
                        .makeSalted(password, storedSalt)
                        .let { it.string == storedEntirePass }
                }
                .getOrElse { false }
                .let { result.value = it }
        }
        return result
    }

    fun register(name: String, address: String, username: String, password: String) =
        Name.makeValidated(name)
            .zip(
                Semigroup.nonEmptyList(),
                Address.makeValidated(address))
            .map { (name, address) ->
                TravelAgency(
                    name = name,
                    address = address,
                    username = Username(username),
                    password = Sha256.makeSalted(password))
            }
            .let {
                when (it) {
                    is Invalid ->
                        throw InvalidObjectException(ValidateUtils.foldValidationErrors(it.value))
                    is Valid ->
                        viewModelScope.launch {
                            agencyDao.insertAll(it.value)
                        }
                }
            }
}