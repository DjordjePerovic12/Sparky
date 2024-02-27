package ltd.bokadev.sparky_social_media.domain.model

data class Comment(
    val id: String,
    val content: String,
    val createdAt: String,
    val author: UserDetails
)