package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentNotificationDto(
    @Json(name = "postId") val postId: String,
    @Json(name = "comment") val comment: CommentResponseDto
)
