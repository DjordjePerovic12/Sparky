package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ltd.bokadev.sparky_social_media.core.utils.getInitials
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun UserImageItem(
    imageUrl: String? = null, userFullName: String
) {

    val frameSize = 35.dp
    val frameShape = RoundedCornerShape(7.dp)
    val color = SparkyTheme.colors.primaryColor
    val path = with(LocalDensity.current) {
        frameSize.toPx()
    }

    //Will handle better after finishing the functionalities of the app
    val stroke = Stroke(
        width = 0f, pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(
                8.75f, 17.5f, 8.75f, 17.5f
            ), path
        )
    )
    Box(
        modifier = Modifier
            .size(frameSize)
            .offset(x = (-frameSize / 2))
            .clip(frameShape)
            .background(SparkyTheme.colors.yellow)
            .drawWithContent {
                this.drawContent()
                drawRoundRect(color = color, style = stroke)
            }, contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .size(frameSize)
            .offset(x = (frameSize / 2))
            .clip(frameShape)
            .background(SparkyTheme.colors.white)
            .drawWithContent {
                this.drawContent()
                drawRoundRect(color = color, style = stroke)
            })
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(SparkyTheme.colors.primaryColor)
        )
        Box(
            modifier = Modifier
                .size(32.dp)
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
                        text = userFullName.getInitials(),
                        style = SparkyTheme.typography.poppinsSemiBold9,
                        color = SparkyTheme.colors.primaryColor
                    )
                }
            } else AsyncImage(
                model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop
            )
        }
    }


}
