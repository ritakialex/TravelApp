package com.steft.travel_app.model

import androidx.room.TypeConverter
import arrow.core.Validated
import com.steft.travel_app.validate.ValidatedObject
import com.steft.travel_app.validate.ValidationError
import java.util.*
import java.util.regex.Pattern

class CorruptDatabaseObjectException(override val message: String) : Exception()

enum class Gender {
    Male,
    Female
}

enum class Specialty {
    Driver,
    Guide,
    Manager
}

/**
 * Only permits validated instances of Address to exist within the runtime
 */
class Address private constructor(val addressString: String) {
    companion object {
        private val validAddressPattern =
            Pattern.compile("^[A-Z][a-z]+ [0-9]+, [A-Z][a-z]+, [A-Z][a-z]+\$")

        fun makeValidated(addressString: String): ValidatedObject<Address> = when {
            validAddressPattern.matcher(addressString).matches() ->
                Validated.Valid(Address(addressString))
            else ->
                Validated.Invalid(ValidationError("Address was invalid"))
        }
    }
}

/**
 * Only permits validated instances of Name to exist within the runtime
 */
class Name private constructor(val nameString: String) {
    companion object {
        private val validAddressPattern =
            Pattern.compile("[A-Za-z ]")

        fun makeValidated(nameString: String): ValidatedObject<Name> = when {
            validAddressPattern.matcher(nameString).matches() ->
                Validated.Valid(Name(nameString))
            else ->
                Validated.Invalid(ValidationError("Name was invalid"))
        }
    }
}

object Converters {
    private const val male = "male"
    private const val female = "female"
    private const val driver = "driver"
    private const val guide = "guide"
    private const val manager = "manager"

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToLong(value: Date): Long = value.time


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
    fun stringToSpecialty(value: String): Specialty = when (value) {
        driver -> Specialty.Driver
        manager -> Specialty.Manager
        guide -> Specialty.Guide
        else -> throw CorruptDatabaseObjectException("Specialty was $value")
    }

    @TypeConverter
    fun specialtyToString(value: Specialty): String = when (value) {
        Specialty.Driver -> driver
        Specialty.Manager -> manager
        Specialty.Guide -> guide
    }

    @TypeConverter
    fun addressToString(value: Address): String = value.addressString

    @TypeConverter
    fun stringToAddress(value: String): Address = Address.makeValidated(value)
        .fold({ throw CorruptDatabaseObjectException(it.message) },
              { it })

    @TypeConverter
    fun nameToString(value: Name): String = value.nameString

    @TypeConverter
    fun stringToHumanName(value: String): Name = Name.makeValidated(value)
        .fold({ throw CorruptDatabaseObjectException(it.message) },
              { it })
}