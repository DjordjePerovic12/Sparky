package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.runtime.Composable
import ltd.bokadev.sparky_social_media.domain.model.CommentNotification
import ltd.bokadev.sparky_social_media.domain.model.Follow
import ltd.bokadev.sparky_social_media.domain.model.LikeNotification
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails

@Composable
fun NotificationItem(
    notifications: NotificationWrapper,
    onUserImageClick: (UserDetails) -> Unit
) {
    when (notifications) {
        is Follow -> {
            FollowNotificationItem(notification = notifications.follows, onUserImageClick = {
                onUserImageClick(it)
            })
        }

        is CommentNotification -> {
            CommentNotificationItem(comment = notifications.comment,
                onUserImageClick = {
                    onUserImageClick(it)
                })
        }

        is LikeNotification -> {
            LikeNotificationItem(user = notifications.user, like = notifications.like,
                onUserImageClick = {
                    onUserImageClick(it)
                })
        }
    }

}