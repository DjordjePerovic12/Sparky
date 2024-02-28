package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseDto(
    @Json(name = "user") val user: UserDto,
    @Json(name = "isFollowing") val isFollowing: Boolean? = null
)
