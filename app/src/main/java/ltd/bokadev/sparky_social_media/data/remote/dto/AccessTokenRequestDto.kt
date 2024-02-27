package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenRequestDto(
    @Json(name = "userId") val userId: String? = null,
    @Json(name = "refreshToken") val refreshToken: String? = null
)
