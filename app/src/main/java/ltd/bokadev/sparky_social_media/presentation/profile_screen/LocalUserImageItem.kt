package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.getInitials
import ltd.bokadev.sparky_social_media.core.utils.noRippleClickable
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun LocalUserImageItem(
    username: String,
    cameraIconSize: Dp = 30.dp,
    imageUrl: String? = null,
    onChangeProfilePictureClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .noRippleClickable {
                onChangeProfilePictureClick()
            }, contentAlignment = Alignment.BottomEnd
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            //Will refactor this when I refactor the image item in general
            //Because the one I made earlier isn't appropriate for this component
            //And my focus at the moment is on functional parts of the app
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(7.dp))
            ) {
                if (imageUrl == null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SparkyTheme.colors.white)
                    ) {
                        Text(
                            text = username.getInitials(),
                            style = SparkyTheme.typography.poppinsSemiBold9,
                            color = SparkyTheme.colors.primaryColor
                        )
                    }
                } else AsyncImage(
                    model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop
                )
            }
        }
        Box(contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.noRippleClickable {
                onChangeProfilePictureClick()
            }) {
            Box(
                modifier = Modifier
                    .size(cameraIconSize)
                    .offset(x = cameraIconSize / 3, cameraIconSize / 3)
                    .clip(RoundedCornerShape(6.dp))
                    .background(SparkyTheme.colors.yellow), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = null,
                    tint = SparkyTheme.colors.primaryColor
                )
            }
        }
    }
}