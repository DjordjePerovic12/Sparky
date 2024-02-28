package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.TopBarStyle
import ltd.bokadev.sparky_social_media.core.utils.formatToTwelveHourMonthNameDateTime
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.presentation.search_screen.SparkySearchBar
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme


//Extracted this out of SparkyTopBar composable
//because it already is too big
//Also because this one is more complex then the other ones
@Composable
fun ProfileScreenTopBar(
    user: User,
    isLoadingUserData: Boolean,
    onLogoutClick: () -> Unit
) {
    //Using static data everywhere because this PR was all about UI
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .background(SparkyTheme.colors.primaryColor)
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
    ) {
        if (isLoadingUserData) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SparkyTheme.colors.primaryColor)
            ) {
                CircularProgressIndicator(color = SparkyTheme.colors.yellow)
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(SparkyTheme.colors.primaryColor)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sparky_logo),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )

                Text(
                    text = stringResource(R.string.sparky),
                    style = SparkyTheme.typography.poppinsMedium24,
                    color = SparkyTheme.colors.white
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .background(SparkyTheme.colors.white.copy(alpha = 0.1f))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SparkyTheme.colors.primaryColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                LocalUserImageItem(
                    username = user.user.username, imageUrl = user.user.profilePictureUrl
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = user.user.username,
                        color = SparkyTheme.colors.white,
                        style = SparkyTheme.typography.poppinsRegular16,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = stringResource(id = R.string.member_since, user.user.registeredAt),
                        color = SparkyTheme.colors.white,
                        style = SparkyTheme.typography.poppinsRegular12,
                        textAlign = TextAlign.Start
                    )
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .background(SparkyTheme.colors.white.copy(alpha = 0.1f))
                    .clickable {
                        onLogoutClick()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfileDataCountItem(count = user.user.postCount.toString(), countedData = "Posts")
            Divider(
                color = SparkyTheme.colors.white.copy(0.1f),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .width(1.dp)
                    .height(20.dp)
            )
            ProfileDataCountItem(
                count = user.user.followerCount.toString(), countedData = "Followers"
            )
            Divider(
                color = SparkyTheme.colors.white.copy(0.1f),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .width(1.dp)
                    .height(20.dp)
            )
            ProfileDataCountItem(
                count = user.user.followingCount.toString(), countedData = "Following"
            )

        }

    }
}