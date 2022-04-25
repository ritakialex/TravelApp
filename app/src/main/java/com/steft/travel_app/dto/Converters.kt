package com.steft.travel_app.dto

import androidx.room.TypeConverter
import com.steft.travel_app.common.*
import java.util.*

class Converters {
    private val male = "male"
    private val female = "female"
    private val roadtrip = "roadtrip"
    private val cruise = "cruise"
    private val independent = "independent"

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToLong(value: Date): Long = value.time

    @TypeConverter
    fun uuidToString(value: UUID): String = value.toString()

    @TypeConverter
    fun dateToLong(value: String): UUID = UUID.fromString(value)


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
    fun stringToSpecialty(value: String): ExcursionType = when (value) {
        roadtrip -> ExcursionType.Roadtrip
        independent -> ExcursionType.Independent
        cruise -> ExcursionType.Cruise
        else -> throw CorruptDatabaseObjectException("ExcursionType was $value")
    }

    @TypeConverter
    fun specialtyToString(value: ExcursionType): String = when (value) {
        ExcursionType.Roadtrip -> roadtrip
        ExcursionType.Cruise -> cruise
        ExcursionType.Independent -> independent
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