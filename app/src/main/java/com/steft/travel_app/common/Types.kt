@file:Suppress("ComplexRedundantLet")

package com.steft.travel_app.common

import androidx.room.TypeConverter
import arrow.core.Validated
import arrow.core.invalidNel
import com.steft.travel_app.common.ValidateUtils
import com.steft.travel_app.common.ValidatedObject
import com.steft.travel_app.common.ValidationError
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern
import kotlin.random.Random

enum class Gender {
    Male,
    Female
}

enum class LocationType {
    Cruise,
    Roadtrip,
    Independent
}

enum class LogTag(val tag: String) {
    Firebase("Firebase"),
    Validation("Validation")
}

/**
 * Only permits validated instances of Address to exist within the runtime
 */
@JvmInline
value class Address private constructor(private val addressString: String) {
    override fun toString() = addressString

    companion object {
        private val validAddressPattern =
            Pattern.compile("^[A-Z][a-z]+ [0-9]+, [A-Z][a-z]+, [A-Z][a-z]+\$")

        fun makeValidated(addressString: String): ValidatedObject<Address> = when {
            validAddressPattern.matcher(addressString).matches() ->
                Validated.Valid(Address(addressString))
            else ->
                ValidationError("'$addressString' is not a valid address.").invalidNel()
        }
    }
}

/**
 * Only permits validated instances of Name to exist within the runtime
 */
@JvmInline
value class Name private constructor(private val nameString: String) {
    override fun toString() = nameString

    companion object {
        private val validAddressPattern =
            Pattern.compile("[A-Za-z ]+")

        fun makeValidated(nameString: String): ValidatedObject<Name> = when {
            validAddressPattern.matcher(nameString).matches() ->
                Validated.Valid(Name(nameString))
            else ->
                ValidationError(
                    "'$nameString' was invalid. Words should start with a capital letter. " +
                            "Only letters and spaces are allowed."
                ).invalidNel()
        }
    }
}

/**
 * Only permits validated instances of Email to exist within the runtime
 */
@JvmInline
value class Email private constructor(private val emailString: String) {
    override fun toString() = emailString

    companion object {
        private val validEmailPattern =
            Pattern.compile(
                "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"" +
                        "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]" +
                        "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
                        "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])" +
                        "|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])" +
                        "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
                        "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"
            )


        fun makeValidated(emailString: String): ValidatedObject<Email> = when {
            validEmailPattern.matcher(emailString).matches() ->
                Validated.Valid(Email(emailString))
            else ->
                ValidationError("'$emailString' is not a valid email address.").invalidNel()
        }
    }
}

/**
 * Only permits validated instances of Phone to exist within the runtime
 */
@JvmInline
value class Phone private constructor(private val phoneString: String) {
    override fun toString() = phoneString

    companion object {
        private val validPhonePattern =
            Pattern.compile("^(\\+\\d{1,2}\\s?)?1?-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}\$")

        fun makeValidated(phoneString: String): ValidatedObject<Phone> = when {
            validPhonePattern.matcher(phoneString).matches() ->
                Validated.Valid(Phone(phoneString))
            else ->
                ValidationError("'$phoneString' is not a valid phone.").invalidNel()
        }
    }
}

@JvmInline
value class Username(val string: String)

@JvmInline
value class Sha256 (val string: String) {
    companion object {
        fun makeSalted(string: String): Sha256 = with(MessageDigest.getInstance("SHA-256")) {


            val salt =
                Random(Random.nextInt(1, 1000)).nextBytes(Random.nextInt(1, 20))
                    .let { String(it, Charset.forName("UTF-8")) }

            digest((string + salt).toByteArray())
                .fold("") { acc, it -> acc + "%02x".format(it) }
                .let { it + salt }
                .let { Sha256(it) }
        }

        fun makeSalted(string: String, salt: String) = with(MessageDigest.getInstance("SHA-256")) {
            digest((string + salt).toByteArray())
                .fold("") { acc, it -> acc + "%02x".format(it) }
                .let { it + salt }
                .let { Sha256(it) }
        }
    }
}


//fun main() {
//    val s = Sha256.makeSalted("asdasd12123").string
//
//    val pass = "asdasd12123"
//    val salt = s.substring(64)
//    println(s)
//    println(Sha256.makeSalted(pass, salt).string)
//
//
//}