@file:Suppress("NAME_SHADOWING")

package com.steft.travel_app.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import arrow.core.*
import arrow.typeclasses.Semigroup
import com.steft.travel_app.common.*
import com.steft.travel_app.dao.AppDatabase
import com.steft.travel_app.dao.firebaseDb
import com.steft.travel_app.dao.registrationDao
import com.steft.travel_app.dto.*
import com.steft.travel_app.model.*
import kotlinx.coroutines.launch
import java.util.*

private typealias PreviewTriple = Triple<UUID, String, String>

class MainViewModelFactory(
    private val application: Application,
    private val travelAgency: UUID? = null) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(application, travelAgency) as T
}

class MainViewModel(application: Application, val travelAgency: UUID?) :
    AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val locationDao = database.locationDao()
    private val agencyDao = database.travelAgencyDao()
    private val bundleDao = database.bundleDao()
    private val registrationDao = firebaseDb().registrationDao()

    private val locationToTriple: (Location) -> PreviewTriple =
        { (id, city, country) ->
            Triple(id, Name.content(city), Name.content(country))
        }

    private val customLocationToTriple: (CustomLocation) -> PreviewTriple =
        { (id, _, city, country) ->
            Triple(id, Name.content(city), Name.content(country))
        }

    private val tripleToPreviewDto: (PreviewTriple) -> LocationPreviewDto =
        { (id, city, country) ->
            LocationPreviewDto(id, city, "$city, $country")
        }

    private val getAllLocations: suspend () -> List<LocationPreviewDto> =
        {
            locationDao
                .getAll()
                .map { locationToTriple(it) }
                .map { tripleToPreviewDto(it) }
        }

    private val getCustomLocationsOfAgency: suspend (UUID) -> List<LocationPreviewDto> =
        { travelAgency ->
            locationDao
                .getAllCustom(travelAgency)
                .map { customLocationToTriple(it) }
                .map { tripleToPreviewDto(it) }
        }

    private val getAllCustomLocations: suspend () -> List<LocationPreviewDto> =
        {
            locationDao
                .getAllCustom()
                .map { customLocationToTriple(it) }
                .map { tripleToPreviewDto(it) }
        }

    private suspend fun bundleToBundlePreviewDto(bundle: Bundle): BundlePreviewDto =
        bundle
            .let { (id, _, locationId, _, price, _, _, _) ->
                getLocationSuspend(locationId)
                    ?.fold(
                        { "${it.city}, ${it.country}" },
                        { "${it.city}, ${it.country}" })
                    ?.let { locationName ->
                        BundlePreviewDto(id, locationName, price.toString())
                    }
                    ?: throw CorruptDatabaseObjectException("Location with id $locationId doesnt exist")
            }

    private fun <T> intoLiveData(t: suspend () -> T) = Utils.intoLiveData<T>(viewModelScope, t)

    private inline fun <T> ifAuthorized(crossinline f: (travelAgency: UUID) -> T): T =
        travelAgency
            ?.let { f(it) }
            ?: throw UnauthorizedException("You aren't logged in as a travel agent")

    private suspend fun getLocationSuspend(locationId: UUID): Either<Location, CustomLocation>? =
        Utils.concurrently(
            { locationDao.findById(locationId) },
            { locationDao.findByIdCustom(locationId) })
        { location, customLocation ->
            location
                ?.let { Either.Left(it) }
                ?: customLocation
                    ?.let { Either.Right(it) }
        }


    fun init() {
        Log.i("MainViewModel", "Initialized main view model")
    }

    fun isLoggedIn() = travelAgency != null

    fun getUserId() = ifAuthorized { it }

    fun getLocation(locationId: UUID): LiveData<LocationDto?> =
        intoLiveData {
            getLocationSuspend(locationId)
                ?.let {
                    when (it) {
                        is Either.Left ->
                            it.value.let { (id, city, country) ->
                                LocationDto(
                                    id = id,
                                    travelAgency = null,
                                    city = Name.content(city),
                                    country = Name.content(country))
                            }
                        is Either.Right ->
                            it.value.let { (id, travelAgency, city, country) ->
                                LocationDto(
                                    id = id,
                                    travelAgency = travelAgency,
                                    city = Name.content(city),
                                    country = Name.content(country))
                            }
                    }
                }
        }

    fun getLocations(): LiveData<List<LocationPreviewDto>> =
        intoLiveData {
            listOf(getAllLocations(), getAllCustomLocations())
                .flatten()
        }


    fun getAgencyLocations(): LiveData<List<LocationPreviewDto>> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                listOf(getAllLocations(), getCustomLocationsOfAgency(travelAgency))
                    .flatten()
            }
        }

    fun getTravelAgency(): LiveData<TravelAgencyDto?> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                agencyDao
                    .findById(travelAgency)
                    ?.let { (id, name, address, username, _) ->
                        TravelAgencyDto(
                            id = id,
                            name = Name.content(name),
                            address = Address.content(address),
                            username = username.string)
                    }
            }
        }


    fun addCustomLocation(city: String, country: String): LiveData<Boolean> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                Name.makeValidated(city)
                    .zip(Semigroup.nonEmptyList(), Name.makeValidated(country))
                    .map { (city, country) ->
                        CustomLocation(
                            travelAgency = travelAgency,
                            city = city,
                            country = country)
                    }.let {
                        when (it) {
                            is Invalid ->
                                throw InvalidObjectException(ValidateUtils.foldValidationErrors(it.value))
                            is Valid ->
                                Either.catch {
                                    locationDao.insertAllCustom(it.value)
                                }.fold({ false }, { true })
                        }
                    }
            }
        }


    fun getBundle(bundleId: UUID): LiveData<BundleDto?> =
        intoLiveData {
            bundleDao
                .findById(bundleId)
                ?.let { (id, agencyId, locationId, date, price, duration, hotels, type) ->
                    BundleDto(
                        id = id,
                        travelAgency = agencyId,
                        location = locationId,
                        date = date,
                        price = price,
                        duration = duration,
                        hotels = hotels.map { Name.content(it) },
                        type = type.name)
                }
        }

    fun getBundles(): LiveData<List<BundlePreviewDto>> =
        intoLiveData {
            bundleDao
                .getAll()
                .map { bundleToBundlePreviewDto(it) }
        }

    fun getBundles(locationId: UUID): LiveData<List<BundlePreviewDto>> =
        intoLiveData {
            bundleDao
                .findByLocation(locationId)
                .map { bundleToBundlePreviewDto(it) }
        }

    fun getAgencyBundles(): LiveData<List<BundlePreviewDto>> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                bundleDao
                    .getAll(travelAgency)
                    .map { bundleToBundlePreviewDto(it) }
            }
        }

    fun addBundle(
        locationId: UUID,
        date: Date,
        price: Double,
        duration: Int,
        hotels: List<String>,
        type: LocationType): LiveData<Boolean> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                hotels
                    .traverseValidated { Name.makeValidated(it) }
                    .map {
                        Bundle(
                            travelAgency = travelAgency,
                            location = locationId,
                            date = date,
                            price = price,
                            duration = duration,
                            hotels = it,
                            type = type)
                    }.let {
                        when (it) {
                            is Valid ->
                                Either.catch {
                                    bundleDao.insertAll(it.value)
                                    registrationDao.insert(Registration(it.value.id, travelAgency, emptyList()))
                                }.fold({ false }, { true })
                            is Invalid ->
                                throw InvalidObjectException(ValidateUtils.foldValidationErrors(it.value))
                        }
                    }
            }
        }

    fun registerCustomer(bundleId: UUID, name: String, surname: String, phone: String, email: String, hotel: String) =
        Name.makeValidated(name)
            .zip(
                Semigroup.nonEmptyList(),
                Name.makeValidated(surname),
                Phone.makeValidated(phone),
                Email.makeValidated(email))
            { name, surname, phone, email ->
                CustomerDetails(name, surname, phone, email, hotel)
            }.let {
                when (it) {
                    is Valid ->
                        viewModelScope.launch {
                            registrationDao.register(bundleId, it.value)
                        }
                    is Invalid ->
                        throw InvalidObjectException(ValidateUtils.foldValidationErrors(it.value))
                }
            }

    fun getAgencyBookings(): LiveData<List<Registration>> =
        ifAuthorized { agencyId ->
            intoLiveData {
                registrationDao.findByAgencyId(agencyId)
            }
        }
}