package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.FollowUnfollowButtonStyle
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun ButtonFollowUnfollow(
    isFollowing: Boolean, style: FollowUnfollowButtonStyle, onClick: () -> Unit
) {
    val sizeModifier = when (style) {
        FollowUnfollowButtonStyle.SEARCH_SCREEN -> {
            if (isFollowing) Modifier.size(
                width = 75.dp, height = 35.dp
            ) else Modifier.size(width = 65.dp, height = 35.dp)
        }

        FollowUnfollowButtonStyle.REMOTE_USER_PROFILE_SCREEN -> {
            Modifier
                .fillMaxWidth()
                .height(40.dp)
        }
    }
    val buttonLabel =
        if (isFollowing) stringResource(id = R.string.unfollow) else stringResource(id = R.string.follow)
    val buttonColor = when (style) {
        FollowUnfollowButtonStyle.SEARCH_SCREEN -> {
            if (isFollowing) SparkyTheme.colors.white else SparkyTheme.colors.yellow
        }

        FollowUnfollowButtonStyle.REMOTE_USER_PROFILE_SCREEN -> {
            if (isFollowing) Color.Transparent else SparkyTheme.colors.yellow
        }
    }
    val buttonBorderColor = when (style) {
        FollowUnfollowButtonStyle.SEARCH_SCREEN -> {
            if (isFollowing) SparkyTheme.colors.red else SparkyTheme.colors.yellow
        }

        FollowUnfollowButtonStyle.REMOTE_USER_PROFILE_SCREEN -> {
            if (isFollowing) SparkyTheme.colors.red else SparkyTheme.colors.yellow
        }
    }

    val buttonLabelColor = when (style) {
        FollowUnfollowButtonStyle.SEARCH_SCREEN -> {
            if (isFollowing) SparkyTheme.colors.red else SparkyTheme.colors.primaryColor
        }

        FollowUnfollowButtonStyle.REMOTE_USER_PROFILE_SCREEN -> {
            if (isFollowing) SparkyTheme.colors.red else SparkyTheme.colors.primaryColor
        }
    }

    Button(
        modifier = sizeModifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = true,
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(11.dp),
        border = BorderStroke(1.dp, buttonBorderColor),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(buttonColor)
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = buttonLabel,
                modifier = Modifier.align(Alignment.Center),
                style = SparkyTheme.typography.poppinsRegular12,
                color = buttonLabelColor,
            )

        }
    }
}