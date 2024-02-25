package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.components.PrimaryButton
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun UserItem(
    user: UserDetails,
    onFollowClick: ((UserDetails) -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            UserImageItem(userFullName = user.username, imageUrl = user.profilePictureUrl)
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = user.username,
                    color = SparkyTheme.colors.primaryColor,
                    style = SparkyTheme.typography.poppinsMedium16
                )
                Text(
                    text = stringResource(R.string.member_since, user.registeredAt),
                    color = SparkyTheme.colors.primaryColor,
                    style = SparkyTheme.typography.poppinsRegular12
                )
            }
        }
        if (user.isFollowing) ButtonUnfollow {

        }
        else ButtonFollow {
            if (onFollowClick != null) {
                onFollowClick(user)
            }
        }
    }
}