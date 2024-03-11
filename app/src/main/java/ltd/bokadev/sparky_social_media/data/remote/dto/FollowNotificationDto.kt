package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FollowNotificationDto(
    @Json(name = "followedUserId") val followedUserId: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "followingUser") val followingUser: UserDto
)
