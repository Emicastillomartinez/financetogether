@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.financetogether.financetogether.util

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

object Validator {
    /**
     * Validates an email address.
     *
     * @param email The email to validate.
     * @return True if the email is valid, false otherwise.
     */
    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validates a password.
     *
     * @param password The password to validate.
     * @return True if the password meets the requirements, false otherwise.
     */
    fun validatePassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() } && password.any { it.isUpperCase() }
    }

    /**
     * Validates a phone number.
     *
     * @param phoneNumber The phone number to validate.
     * @param countryCode The ISO 3166-1 two-letter country code.
     * @return True if the phone number is valid, false otherwise.
     */
    fun validatePhoneNumber(
        phoneNumber: String,
        countryCode: String = "US",
    ): Boolean {
        val phoneUtil = com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance()
        return try {
            val numberProto = phoneUtil.parse(phoneNumber, countryCode)
            phoneUtil.isValidNumber(numberProto)
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Formats a phone number to international format.
     *
     * @param phoneNumber The phone number to format.
     * @param countryCode The ISO 3166-1 two-letter country code.
     * @return The formatted phone number.
     */
    fun formatPhoneNumber(
        phoneNumber: String,
        countryCode: String = "ES",
    ): String {
        val phoneUtil = com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance()
        return try {
            val numberProto = phoneUtil.parse(phoneNumber, countryCode)
            phoneUtil.format(numberProto, com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: Exception) {
            phoneNumber
        }
    }

    /**
     * Validates a date.
     *
     * @param date The date to validate.
     * @param format The format to use for validation.
     * @return True if the date is valid, false otherwise.
     */
    fun validateDate(
        date: String,
        format: String = "yyyy-MM-dd",
    ): Boolean {
        return try {
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            formatter.isLenient = false
            formatter.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Validates a username.
     *
     * @param username The username to validate.
     * @return True if the username meets the requirements, false otherwise.
     */
    fun validateUsername(username: String): Boolean {
        return username.matches(Regex("^[a-zA-Z0-9_]{5,15}$"))
    }

    /**
     * Validates a URL.
     *
     * @param url The URL to validate.
     * @return True if the URL is valid, false otherwise.
     */
    fun validateURL(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }
}
