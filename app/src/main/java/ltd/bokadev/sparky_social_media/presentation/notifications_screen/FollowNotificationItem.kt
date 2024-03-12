package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.foundation.clickable
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
import ltd.bokadev.sparky_social_media.domain.model.FollowNotification
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.presentation.utils.formatToHHMM
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun FollowNotificationItem(
    notification: FollowNotification,
    onUserImageClick: (UserDetails) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        UserImageItem(
            userFullName = notification.followingUser.username,
            imageUrl = notification.followingUser.profilePictureUrl,
            modifier = Modifier.clickable {
                onUserImageClick(notification.followingUser)
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = SparkyTheme.colors.black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                        ),
                    ) {
                        append(notification.followingUser.username)
                    }

                    withStyle(
                        style = SpanStyle(
                            color = SparkyTheme.colors.lightGray,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        )
                    ) {
                        append(stringResource(R.string.follows_you_now))
                    }
                })
            }
            Text(
                text = notification.createdAt.formatToHHMM(),
                color = SparkyTheme.colors.notificationHourColor,
                style = SparkyTheme.typography.poppinsRegular12
            )
        }

    }
}