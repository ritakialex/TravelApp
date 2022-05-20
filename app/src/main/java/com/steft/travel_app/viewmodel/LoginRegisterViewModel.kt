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
import java.util.*


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val agencyDao = database.travelAgencyDao()

    private fun <T> intoLiveData(t: suspend () -> T) = Utils.intoLiveData(viewModelScope, t)

    fun login(username: String, password: String): LiveData<UUID?> =
        MutableLiveData<UUID?>()
            .also { result ->
                viewModelScope.launch {
                    agencyDao
                        .findByUsername(Username(username))
                        ?.let { agency ->
                            Either.catch { agency.password.string to Sha256.split(agency.password).second }
                                .map { (storedEntirePass, storedSalt) ->
                                    Sha256
                                        .makeSalted(password, storedSalt)
                                        .let { it.string == storedEntirePass }
                                }
                                .fold({ null }, { if (it) agency.id else null })
                        }
                        .let { result.value = it }
                }
            }


    fun register(name: String, address: String, username: String, password: String): LiveData<Boolean> =
        intoLiveData {
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
                            Either.catch { agencyDao.insertAll(it.value) }
                                .fold({ false }, { true })
                    }
                }
        }
}