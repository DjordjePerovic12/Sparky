package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.noRippleClickable
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun NotificationScreenTopBar(
    onBackClick: () -> Unit
) {
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .background(SparkyTheme.colors.white.copy(alpha = 0.1f))
                    .noRippleClickable {
                        onBackClick()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Text(
                text = stringResource(R.string.notifications),
                color = SparkyTheme.colors.white,
                style = SparkyTheme.typography.poppinsMedium16,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}


@Preview
@Composable
fun TopBarPreview() {
    NotificationScreenTopBar()
}