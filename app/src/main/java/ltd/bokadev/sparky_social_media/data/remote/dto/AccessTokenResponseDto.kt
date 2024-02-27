package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenResponseDto(
    @Json(name = "accessToken") val accessToken: String? = null,
    @Json(name = "expirationTimestamp") val expirationTimeStamp: Long? = null
)
