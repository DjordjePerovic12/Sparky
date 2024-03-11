package ltd.bokadev.sparky_social_media.domain.model

data class Notifications(
    val follows: List<Follow>,
    val likes: List<LikeNotification>,
    val comments: List<CommentNotification>
)
