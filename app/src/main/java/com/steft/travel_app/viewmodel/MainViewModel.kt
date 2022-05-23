@file:Suppress("NAME_SHADOWING", "NestedLambdaShadowedImplicitParameter")

package com.steft.travel_app.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.DayOfWeek
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

    private val getLocationsOfAgency: suspend (UUID) -> List<LocationPreviewDto> =
        { travelAgency ->
            locationDao
                .getAllOfAgency(travelAgency)
                .map { locationToTriple(it) }
                .map { tripleToPreviewDto(it) }
        }


    private suspend fun bundleToBundlePreviewDto(bundle: Bundle): BundlePreviewDto =
        bundle
            .let { (id, _, locationId, _, price, _, _, _) ->
                locationDao.findByIdAll(locationId)
                    ?.let {
                        "${it.city}, ${it.country}"
                    }
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

    fun init() {
        Log.i("MainViewModel", "Initialized main view model")
    }

    fun isLoggedIn() = travelAgency != null

    fun getUserId() = ifAuthorized { it }

    fun getLocation(locationId: UUID): LiveData<LocationDto?> =
        intoLiveData {
            locationDao.findByIdAll(locationId)
                ?.let {
                    it.let { (id, _, city, country) ->
                        LocationDto(
                            id = id,
                            travelAgency = null,
                            city = Name.content(city),
                            country = Name.content(country))
                    }
                }
        }

    fun getLocations(): LiveData<List<LocationPreviewDto>> =
        intoLiveData {
            getAllLocations()
        }


    fun getAgencyLocations(): LiveData<List<LocationPreviewDto>> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                getLocationsOfAgency(travelAgency)
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
                        Location(
                            travelAgency = travelAgency,
                            city = city,
                            country = country)
                    }.let {
                        when (it) {
                            is Invalid ->
                                throw InvalidObjectException(ValidateUtils.foldValidationErrors(it.value))
                            is Valid ->
                                Either.catch {
                                    locationDao.insert(it.value)
                                }.fold({ false }, { true })
                        }
                    }
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getBundle(bundleId: UUID): LiveData<BundleDto?> =
        intoLiveData {
            bundleDao
                .findById(bundleId)
                ?.let { (id, agencyId, locationId, date, price, duration, hotels, type) ->

                    val dateStr = Calendar
                        .getInstance()
                        .apply { time = date }
                        .run {
                            "${DayOfWeek.of(Calendar.DAY_OF_MONTH)} " +
                                    "${get(Calendar.DAY_OF_MONTH)}/" +
                                    "${get(Calendar.MONTH) + 1}/" +
                                    "${get(Calendar.YEAR)} "
                        }

                    val travelAgency = agencyDao
                        .findById(agencyId)
                        ?.name
                        ?.let { Name.content(it) }
                        ?: throw IllegalArgumentException("Agency doesnt exist")

                    BundleDto(
                        id = id,
                        travelAgency = travelAgency,
                        location = locationId,
                        date = dateStr,
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
                                Either
                                    .catch {
                                        bundleDao.insertAll(it.value)
                                        registrationDao.insert(Registration(it.value.id, travelAgency, emptyList()))
                                    }
                                    .tapLeft { Log.e("Database", it.toString()) }
                                    .fold({ false }, { true })
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

    fun getAgencyBookings(): LiveData<List<RegistrationPreviewDto>> =
        ifAuthorized { agencyId ->
            intoLiveData {
                registrationDao.findByAgencyId(agencyId)
                    .flatMap { (bundle, _, customers) ->
                        bundleDao.findById(bundle)
                            ?.let { (_, _, location) ->
                                val toPreview: (Name, Name) -> List<RegistrationPreviewDto> =
                                    { city, country ->
                                        customers.map { (name, surname, phone, email, hotel) ->
                                            val locationName = "$city, $country"
                                            val customer = "$name $surname\n$phone\n$email\n$hotel"
                                            RegistrationPreviewDto(bundle, locationName, customer)
                                        }
                                    }

                                locationDao.findByIdAll(location)
                                    ?.let { (_, _, city, country) -> toPreview(city, country) }

                            } ?: throw IllegalArgumentException("Error in retrieving bookings")
                    }
            }
        }


    fun deleteCustomLocation(id: UUID): LiveData<Boolean> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                Either
                    .catch {
                        locationDao.deleteCustom(id, travelAgency)
                    }
                    .fold({ false }, { it > 0 })
            }
        }

    fun deleteTravelAgency(): LiveData<Boolean> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                Either
                    .catch {
                        agencyDao.delete(travelAgency)
                    }
                    .fold({ false }, { it > 0 })
            }
        }

    fun deleteBundle(bundleId: UUID): LiveData<Boolean> =
        ifAuthorized { travelAgency ->
            intoLiveData {
                registrationDao.run {
                    if (registrationsExist(bundleId))
                        false
                    else
                        Either.catch {
                            bundleDao.delete(bundleId, travelAgency)
                        }.fold({ false }, { it > 0 })
                }
            }
        }

    fun updateBundle(
        bundleId: UUID,
        locationId: UUID? = null,
        date: Date? = null,
        price: Double? = null,
        duration: Int? = null,
        hotels: List<String>? = null,
        type: LocationType? = null): LiveData<Boolean> =
        ifAuthorized {
            intoLiveData {
                bundleDao.findById(bundleId)
                    ?.let { (oldId, oldAgency, oldLocation, oldDate, oldPrice, oldDuration, oldHotels, oldType) ->

                        val hotels = hotels
                            ?.traverseValidated { hotel ->
                                Name.makeValidated(hotel)
                            }
                            ?.mapLeft { IllegalArgumentException(ValidateUtils.foldValidationErrors(it)) }
                            ?.toEither() ?: Either.Right(oldHotels)

                        hotels
                            .map { hotels ->
                                Bundle(
                                    id = oldId,
                                    travelAgency = oldAgency,
                                    location = locationId ?: oldLocation,
                                    date = date ?: oldDate,
                                    price = price ?: oldPrice,
                                    duration = duration ?: oldDuration,
                                    hotels = hotels,
                                    type = type ?: oldType)
                            }
                            .map { bundleDao.update(it) }
                            .fold({ throw it }, { true })
                    }
                    .let { it ?: throw IllegalArgumentException("Bundle not found") }

            }

        }
}

