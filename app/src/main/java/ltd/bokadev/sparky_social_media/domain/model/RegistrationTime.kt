package ltd.bokadev.sparky_social_media.domain.model

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class RegistrationTime(
    val formattedTime: String
) {
    companion object {
        fun convertToZonedDateTime(utcTime: String?): ZonedDateTime? {
            if (utcTime.isNullOrEmpty()) return null
            return try {
                ZonedDateTime.parse(utcTime)
            } catch (e: Exception) {
                null
            }
        }


        fun formatDateTime(zonedDateTime: ZonedDateTime?): String {
            if (zonedDateTime == null) return ""
            return try {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.")
                zonedDateTime.format(formatter)
            } catch (e: Exception) {
                ""
            }
        }
    }
}
