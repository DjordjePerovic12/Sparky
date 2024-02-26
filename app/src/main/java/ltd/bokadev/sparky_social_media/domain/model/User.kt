package ltd.bokadev.sparky_social_media.domain.model

data class User(
    val user: UserDetails, val isFollowing: Boolean
)
