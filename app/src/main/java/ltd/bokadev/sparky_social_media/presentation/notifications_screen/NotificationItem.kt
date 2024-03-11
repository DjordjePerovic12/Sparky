package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.runtime.Composable
import ltd.bokadev.sparky_social_media.domain.model.CommentNotification
import ltd.bokadev.sparky_social_media.domain.model.Follow
import ltd.bokadev.sparky_social_media.domain.model.LikeNotification
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper

@Composable
fun NotificationItem(notifications: NotificationWrapper) {
    when (notifications) {
        is Follow -> {
            FollowNotificationItem(notification = notifications.follows)
        }

        is CommentNotification -> {
            CommentNotificationItem(comment = notifications.comment)
        }

        is LikeNotification -> {
            LikeNotificationItem(user = notifications.user, like = notifications.like)
        }
    }

}