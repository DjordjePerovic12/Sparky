package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentRequestDto(
    @Json(name = "postId") val postId: String? = null,
    @Json(name = "content") val content: String? = null
)
