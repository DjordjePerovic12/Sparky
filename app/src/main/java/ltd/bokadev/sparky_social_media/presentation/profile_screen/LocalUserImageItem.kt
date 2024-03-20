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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.getInitials
import ltd.bokadev.sparky_social_media.core.utils.noRippleClickable
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme
import okhttp3.Dispatcher
import timber.log.Timber

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LocalUserImageItem(
    username: String,
    cameraIconSize: Dp = 30.dp,
    imageUrl: String? = null,
    onChangeProfilePictureClick: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(50.dp)
            .noRippleClickable {
                context.imageLoader.diskCache?.clear()
                context.imageLoader.memoryCache?.clear()

                onChangeProfilePictureClick()
                Timber.e("Image urL $imageUrl")
            }, contentAlignment = Alignment.BottomEnd
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            UserImageItem(modifier = Modifier.clickable {
                onChangeProfilePictureClick()
            }, frameSize = 50.dp, userFullName = username, imageUrl = imageUrl)
        }
        Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.noRippleClickable {
            onChangeProfilePictureClick()
        }) {
            Box(
                modifier = Modifier
                    .size(cameraIconSize)
                    .offset(x = cameraIconSize / 3, cameraIconSize / 3)
                    .clip(RoundedCornerShape(6.dp))
                    .background(SparkyTheme.colors.yellow),
                contentAlignment = Alignment.Center
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