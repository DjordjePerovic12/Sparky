package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponseDto(
    @Json(name = "accessToken") val accessToken: String? = null,
    @Json(name = "refreshToken") val refreshToken: String? = null,
    @Json(name = "accessTokenExpirationTimestamp") val accessTokenExpirationTimeStamp: String? = null,
    @Json(name = "userId") val userId: String? = null,
    @Json(name = "username") val username: String? = null
)
