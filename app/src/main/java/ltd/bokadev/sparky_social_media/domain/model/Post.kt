package ltd.bokadev.sparky_social_media.domain.model

data class Post(
    val id: String,
    val content: String,
    val createdAt: String,
    val commentCount: Long,
    val likeCount: Long,
    val isLiked: Boolean,
    val author: UserDetails
)
