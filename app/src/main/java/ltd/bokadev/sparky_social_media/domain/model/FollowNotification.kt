package ltd.bokadev.sparky_social_media.domain.model

data class FollowNotification(
    val followedUserId: String,
    val createdAt: String,
    val followingUser: UserDetails
)
