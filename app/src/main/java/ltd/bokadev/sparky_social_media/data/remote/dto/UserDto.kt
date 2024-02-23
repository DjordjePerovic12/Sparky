package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "username") val username: String? = null,
    @Json(name = "profilePictureUrl") val profilePictureUrl: String? = null,
    @Json(name = "registeredAt") val registeredAt: String? = null,
    @Json(name = "postCount") val postCount: Long? = null,
    @Json(name = "followerCount") val followerCount: Long? = null,
    @Json(name = "followingCount") val followingCount: Long? = null
)
