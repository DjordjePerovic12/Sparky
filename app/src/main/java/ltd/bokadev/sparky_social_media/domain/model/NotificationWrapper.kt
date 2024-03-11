package ltd.bokadev.sparky_social_media.domain.model

open class NotificationWrapper(
    val createAt: String
)

data class Follow(
    val follows: FollowNotification
) : NotificationWrapper(follows.createdAt)

data class LikeNotification(
    val user: UserDetails, val like: Like
) : NotificationWrapper(like.createdAt)

data class CommentNotification(
    val postId: String, val comment: Comment
) : NotificationWrapper(comment.createdAt)

