package ltd.bokadev.sparky_social_media.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotificationsResponseDto(
    @Json(name = "follows") val follows: List<FollowDto>,
    @Json(name = "likes") val likes: List<LikeNotificationDto>,
    @Json(name = "comments") val comments: List<CommentNotificationDto>
)
