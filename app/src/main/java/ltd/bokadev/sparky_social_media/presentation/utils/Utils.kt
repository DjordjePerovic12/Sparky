package ltd.bokadev.sparky_social_media.presentation.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun String.formatToHHMM(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
        val dateTime = LocalDateTime.parse(this, formatter)
        val formattedTime = DateTimeFormatter.ofPattern("HH:mm").format(dateTime)
        formattedTime
    } catch (e: DateTimeParseException) {
        // Handle parsing errors, return empty string, or log the error
        "Invalid Date-Time Format"
    }
}

fun String.formatDate(): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
        val dateTime = LocalDateTime.parse(this, formatter)
        val date = dateTime.toLocalDate()

        val currentDate = LocalDate.now()
        val yesterdayDate = currentDate.minusDays(1)

        when (date) {
            currentDate -> "Today"
            yesterdayDate -> "Yesterday"
            else -> DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date)
        }
    } catch (e: DateTimeParseException) {
        // Handle parsing errors, return empty string, or log the error
        "Invalid Date-Time Format"
    }
}