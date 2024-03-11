package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LikeNotificationDto(
    @Json(name = "user") val user: UserDto,
    @Json(name = "like") val like: LikeDto,
)
