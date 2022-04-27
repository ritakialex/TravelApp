package com.steft.travel_app.dto

import androidx.room.TypeConverter
import com.steft.travel_app.common.*
import java.util.*

object Converters {
    private const val male = "male"
    private const val female = "female"
    private const val roadtrip = "roadtrip"
    private const val cruise = "cruise"
    private const val independent = "independent"

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToLong(value: Date): Long = value.time

    @TypeConverter
    fun uuidToString(value: UUID): String = value.toString()

    @TypeConverter
    fun stringToUuid(value: String): UUID = UUID.fromString(value)

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
    fun stringToSpecialty(value: String): LocationType = when (value) {
        roadtrip -> LocationType.Roadtrip
        independent -> LocationType.Independent
        cruise -> LocationType.Cruise
        else -> throw CorruptDatabaseObjectException("LocationType was $value")
    }

    @TypeConverter
    fun specialtyToString(value: LocationType): String = when (value) {
        LocationType.Roadtrip -> roadtrip
        LocationType.Cruise -> cruise
        LocationType.Independent -> independent
    }

    @TypeConverter
    fun addressToString(value: Address): String = value.toString()

    @TypeConverter
    fun stringToAddress(value: String): Address = Address.makeValidated(value)
        .fold({ throw CorruptDatabaseObjectException(ValidateUtils.foldValidationErrors(it)) },
              { it })

    @TypeConverter
    fun nameToString(value: Name): String = value.toString()

    @TypeConverter
    fun stringToHumanName(value: String): Name = Name.makeValidated(value)
        .fold({ throw CorruptDatabaseObjectException(ValidateUtils.foldValidationErrors(it)) },
              { it })
}