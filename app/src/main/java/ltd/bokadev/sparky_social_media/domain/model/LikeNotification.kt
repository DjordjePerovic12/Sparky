package ltd.bokadev.sparky_social_media.domain.model

data class LikeNotification(
    val user: UserDetails,
    val like: Like
)
