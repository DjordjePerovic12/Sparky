package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun ButtonFollowUnfollow(isFollowing: Boolean, onClick: () -> Unit) {
    val width = if (isFollowing) 75.dp else 65.dp
    val buttonLabel =
        if (isFollowing) stringResource(id = R.string.unfollow) else stringResource(id = R.string.follow)
    val buttonColor = if (isFollowing) SparkyTheme.colors.white else SparkyTheme.colors.yellow
    val buttonBorderColor = if (isFollowing) SparkyTheme.colors.red else SparkyTheme.colors.yellow
    val buttonLabelColor =
        if (isFollowing) SparkyTheme.colors.red else SparkyTheme.colors.primaryColor
    Button(
        modifier = Modifier.size(width = width, height = 35.dp),
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