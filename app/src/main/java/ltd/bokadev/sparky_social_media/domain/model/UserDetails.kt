package ltd.bokadev.sparky_social_media.domain.model

import com.squareup.moshi.JsonClass

data class UserDetails(
    val id: String,
    val username: String,
    val profilePictureUrl: String,
    val registeredAt: String,
    val postCount: Long,
    val followerCount: Long,
    val followingCount: Long,
    val isFollowing: Boolean
)
