package com.steft.travel_app.dto

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.getOrElse
import com.google.gson.Gson
import com.steft.travel_app.common.*
import org.json.JSONObject
import java.util.*
import kotlin.math.E

object Converters {
    private const val male = "male"
    private const val female = "female"
    private const val roadtrip = "roadtrip"
    private const val cruise = "cruise"
    private const val independent = "independent"


    @TypeConverter
    fun namesToJson(value: List<Name>): String =
        Gson().toJson(value.map { Name.content(it) })

    @TypeConverter
    fun jsonToNames(value: String): List<Name> =
        Gson()
            .fromJson(value, Array<String>::class.java)
            .toList()
            .map { Name.makeValidated(it) }
            .map {
                when (it) {
                    is Valid -> it.value
                    is Invalid -> throw CorruptDatabaseObjectException("Name was $value")
                }
            }

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToLong(value: Date): Long = value.time

    @TypeConverter
    fun uuidToStringNullable(value: UUID?): String = value.toString()

    @TypeConverter
    fun stringToUuidNullable(value: String?): UUID? = UUID.fromString(value)

    @TypeConverter
    fun usernameToString(value: Username): String = value.string

    @TypeConverter
    fun stringToUsername(value: String): Username = Username(value)

    @TypeConverter
    fun shaToString(value: Sha256): String = value.string

    @TypeConverter
    fun stringToSha(value: String): Sha256 = Sha256(value)

    @TypeConverter
    fun stringToGender(value: String): Gender = when (value) {
        male -> Gender.Male
        female -> Gender.Female
        else -> throw CorruptDatabaseObjectException("Gender was $value")
    }

    @TypeConverter
    fun genderToString(value: Gender): String = when (value) {
        Gender.Male -> male
        Gender.Female -> female
    }

    @TypeConverter
    fun stringToLocationType(value: String): LocationType = when (value) {
        roadtrip -> LocationType.Roadtrip
        independent -> LocationType.Independent
        cruise -> LocationType.Cruise
        else -> throw CorruptDatabaseObjectException("LocationType was $value")
    }

    @TypeConverter
    fun locationTypeToString(value: LocationType): String = when (value) {
        LocationType.Roadtrip -> roadtrip
        LocationType.Cruise -> cruise
        LocationType.Independent -> independent
    }

    @TypeConverter
    fun addressToString(value: Address): String = value.toString()

    @TypeConverter
    fun stringToAddress(value: String): Address =
        Address.makeValidated(value)
            .fold({ throw CorruptDatabaseObjectException(ValidateUtils.foldValidationErrors(it)) },
                { it })

    @TypeConverter
    fun nameToString(value: Name): String = value.toString()

    @TypeConverter
    fun stringToName(value: String): Name =
        Name.makeValidated(value)
            .fold({ throw CorruptDatabaseObjectException(ValidateUtils.foldValidationErrors(it)) },
                { it })
}