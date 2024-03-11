package ltd.bokadev.sparky_social_media.domain.model

sealed class NotificationWrapper {
    data class Follow(
        val follows: FollowNotification
    ) : NotificationWrapper()

    data class LikeNotification(
        val user: UserDetails, val like: Like
    ) : NotificationWrapper()

    data class CommentNotification(
        val postId: String, val comment: Comment
    ) : NotificationWrapper()
}