package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ltd.bokadev.sparky_social_media.domain.model.UserData

@JsonClass(generateAdapter = true)
data class PostResponseDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "content") val content: String? = null,
    @Json(name = "createdAt") val createdAt: String? = null,
    @Json(name = "commentCount") val commentCount: Long? = null,
    @Json(name = "likeCount") val likeCount: Long? = null,
    @Json(name = "isLiked") val isLiked: Boolean? = null,
    @Json(name = "author") val author: UserDto? = null
)
