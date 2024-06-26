package ltd.bokadev.sparky_social_media.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.TopBarStyle
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun SparkyTopBar(
    style: String = "Default"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
            .background(SparkyTheme.colors.primaryColor)
            .systemBarsPadding()
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sparky_logo), contentDescription = null,
                modifier = Modifier.size(36.dp)
            )

            Text(
                text = stringResource(R.string.sparky),
                style = SparkyTheme.typography.poppinsMedium24,
                color = SparkyTheme.colors.white
            )
        }
        when (style) {
            TopBarStyle.HOME.style -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .size(40.dp)
                            .background(SparkyTheme.colors.white.copy(alpha = 0.1f))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

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
            }

            TopBarStyle.PROFILE.style -> {
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
        }
    }
}