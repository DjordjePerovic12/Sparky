package ltd.bokadev.sparky_social_media.domain.model

data class CommentRequest(
    val postId: String, val content: String
)
