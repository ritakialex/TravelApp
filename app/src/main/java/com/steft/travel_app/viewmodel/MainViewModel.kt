package com.steft.travel_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import arrow.core.*
import arrow.typeclasses.Semigroup
import com.steft.travel_app.common.*
import com.steft.travel_app.dao.AppDatabase
import com.steft.travel_app.dto.LocationPreviewDto
import com.steft.travel_app.dto.TravelAgencyDto
import com.steft.travel_app.model.TravelAgency
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val locationDao = database.locationDao()
    private val agencyDao = database.travelAgencyDao()

    fun getLocations(travelAgency: UUID): LiveData<List<LocationPreviewDto>> {
        val result = MutableLiveData<List<LocationPreviewDto>>()
        viewModelScope.launch {
            val preExistingLocations =
                locationDao
                    .getAll()
                    .map { (id, city, country) ->
                        Triple(id, Name.content(city), Name.content(country))
                    }
                    .map { (id, city, country) ->
                        LocationPreviewDto(id, city, "$city, $country")
                    }

            val customLocations =
                locationDao
                    .getAllCustom(travelAgency)
                    .map { (id, _, city, country) ->
                        Triple(id, Name.content(city), Name.content(country))
                    }
                    .map { (id, city, country) ->
                        LocationPreviewDto(id, city, "$city, $country")
                    }

            result.value =
                listOf(preExistingLocations, customLocations)
                    .flatten()
        }
        return result
    }

    fun getLocations(): LiveData<List<LocationPreviewDto>> {
        val result = MutableLiveData<List<LocationPreviewDto>>()
        viewModelScope.launch {
            val customLocations =
                locationDao
                    .getAllCustom()
                    .map { (id, _, city, country) ->
                        Triple(id, Name.content(city), Name.content(country))
                    }
                    .map { (id, city, country) ->
                        LocationPreviewDto(id, city, "$city, $country")
                    }

            val preExistingLocations =
                locationDao
                    .getAll()
                    .map { (id, city, country) ->
                        Triple(id, Name.content(city), Name.content(country))
                    }
                    .map { (id, city, country) ->
                        LocationPreviewDto(id, city, "$city, $country")
                    }

            result.value =
                listOf(preExistingLocations, customLocations)
                    .flatten()
        }
        return result
    }

    fun getTravelAgency(travelAgency: UUID): LiveData<TravelAgencyDto> {
        val result = MutableLiveData<TravelAgencyDto>()
        viewModelScope.launch {
            val agency =
                agencyDao
                    .findById(travelAgency)
                    .let { (id, name, address, username, _) ->
                        TravelAgencyDto(
                            id, Name.content(name),
                            Address.content(address),
                            username.string)
                    }
            result.value = agency
        }
        return result
    }

}