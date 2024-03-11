package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.formatToTwelveHourMonthNameDateTime
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper
import ltd.bokadev.sparky_social_media.domain.model.Notifications
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.presentation.utils.formatToHHMM
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun NotificationItem(notifications: NotificationWrapper) {
    when (notifications) {
        is NotificationWrapper.Follow -> {
            FollowNotificationItem(notification = notifications.follows)
        }

        is NotificationWrapper.CommentNotification -> {
            CommentNotificationItem(comment = notifications.comment)
        }

        is NotificationWrapper.LikeNotification -> {
            LikeNotificationItem(user = notifications.user, like = notifications.like)
        }
    }

}