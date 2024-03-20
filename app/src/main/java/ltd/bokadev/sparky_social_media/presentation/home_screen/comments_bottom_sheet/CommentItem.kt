package ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.core.utils.formatToTwelveHourMonthNameDateTime
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.presentation.notifications_screen.NotificationsScreenEvent
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun CommentItem(
    comment: Comment,
    onUserImageClick: (UserDetails) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .background(SparkyTheme.colors.primaryColor),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        UserImageItem(
            userFullName = comment.author.username, imageUrl = comment.author.profilePictureUrl,
            frameSize = 30.dp,
            modifier = Modifier.clickable {
                onUserImageClick(comment.author)
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
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = comment.author.username,
                    color = SparkyTheme.colors.white,
                    style = SparkyTheme.typography.poppinsMedium14
                )
                Text(
                    text = comment.createdAt.formatToTwelveHourMonthNameDateTime(),
                    color = SparkyTheme.colors.white.copy(0.3f),
                    style = SparkyTheme.typography.poppinsRegular12
                )
            }
            Text(
                text = comment.content,
                color = SparkyTheme.colors.white,
                style = SparkyTheme.typography.poppinsRegular14
            )
        }

    }
}