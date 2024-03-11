package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LikeDto(
    @Json(name = "userId") val userId: String,
    @Json(name  = "postId") val postId: String,
    @Json(name = "createdAt") val createdAt: String
)
