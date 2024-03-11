package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.data.remote.dto.CommentNotificationDto
import ltd.bokadev.sparky_social_media.data.remote.dto.FollowDto
import ltd.bokadev.sparky_social_media.data.remote.dto.FollowNotificationDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LikeDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LikeNotificationDto
import ltd.bokadev.sparky_social_media.data.remote.dto.NotificationsResponseDto
import ltd.bokadev.sparky_social_media.domain.model.CommentNotification
import ltd.bokadev.sparky_social_media.domain.model.Follow
import ltd.bokadev.sparky_social_media.domain.model.FollowNotification
import ltd.bokadev.sparky_social_media.domain.model.Like
import ltd.bokadev.sparky_social_media.domain.model.LikeNotification
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper

fun List<FollowDto>.toFollow(): List<NotificationWrapper> {
    return this.map { followDto ->
        Follow(
            follows = followDto.follow.toFollowNotification()
        )
    }
}

fun FollowNotificationDto.toFollowNotification(): FollowNotification {
    return FollowNotification(
        followedUserId = this.followedUserId,
        followingUser = this.followingUser.toUserDetails(),
        createdAt = this.createdAt
    )
}

fun List<CommentNotificationDto>.toCommentNotification(): List<NotificationWrapper> {
    return this.map { commentNotificationDto ->
        CommentNotification(
            postId = commentNotificationDto.postId,
            comment = commentNotificationDto.comment.toComment()
        )
    }
}

fun LikeDto.toLike(): Like {
    return Like(
        postId = this.postId, createdAt = this.createdAt, userId = this.userId
    )
}

fun List<LikeNotificationDto>.toLikeNotifications(): List<NotificationWrapper> {
    return this.map { likeNotificationDto ->
        LikeNotification(
            user = likeNotificationDto.user.toUserDetails(),
            like = likeNotificationDto.like.toLike()
        )
    }
}


fun NotificationsResponseDto.toNotificationCreatedAt(): List<NotificationWrapper> {
    return this.follows.toFollow() + this.comments.toCommentNotification() + this.likes.toLikeNotifications()
}