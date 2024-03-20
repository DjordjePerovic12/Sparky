package ltd.bokadev.sparky_social_media.presentation.home_screen

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ltd.bokadev.sparky_social_media.core.utils.getInitials
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun UserImageItem(
    modifier: Modifier = Modifier,
    frameSize: Dp,
    imageUrl: String? = null, userFullName: String
) {
    val leftSideColor = SparkyTheme.colors.yellow
    val rightSideColor = SparkyTheme.colors.white

    BoxWithConstraints(
        modifier = modifier.then(Modifier.size(frameSize)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(7.dp))
        ) {
            val itemWidth = constraints.maxWidth.toFloat()
            val itemHeight = constraints.maxHeight.toFloat()
            val frameThickness = 5f // Adjust the thickness of the frame as needed
            val thirdWidth = itemWidth / 3
            val thirdHeight = itemHeight / 3
            val cornerRadius = 7.dp.toPx()

            val topLeftSide = Path().apply {
                moveTo(0f, 0f)
                lineTo(thirdWidth, 0f) // Draw line from top left towards the middle of the top side
                moveTo(0f, 0f)
                lineTo(
                    0f,
                    thirdHeight
                ) // Draw line from the top left towards the middle of the left side
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = cornerRadius * 2,
                        bottom = cornerRadius * 2
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }

            drawPath(
                path = topLeftSide,
                color = leftSideColor,
                style = Stroke(width = frameThickness, cap = StrokeCap.Round)
            ) //Round the line on the angle intersection

            val bottomLeftSide = Path().apply {
                moveTo(0f, itemHeight)
                lineTo(
                    thirdWidth,
                    itemHeight
                ) // Draw line from the bottom left towards the middle of hte bottom side
                arcTo(
                    rect = Rect(
                        left = 0f,
                        top = size.height - 2 * cornerRadius,
                        right = 2 * cornerRadius,
                        bottom = size.height
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                moveTo(0f, itemHeight)
                lineTo(
                    0f,
                    itemHeight - thirdHeight
                ) // Draw line from the bottom to a third of the left side
            }

            drawPath(
                path = bottomLeftSide,
                color = leftSideColor,
                style = Stroke(width = frameThickness, cap = StrokeCap.Round)
            )

            val topRightSide = Path().apply {
                moveTo(itemWidth, 0f)
                lineTo(
                    itemWidth,
                    thirdHeight
                ) // Draw line from topRight to a third of the right side
                arcTo(
                    rect = Rect(
                        left = itemWidth - 2 * cornerRadius,
                        top = 0f,
                        right = itemWidth,
                        bottom = 2 * cornerRadius
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
                moveTo(itemWidth, 0f)
                lineTo(
                    itemWidth - thirdWidth,
                    0f
                ) // Draw line from the top right towards the middle
            }

            drawPath(
                path = topRightSide,
                color = rightSideColor,
                style = Stroke(width = frameThickness, cap = StrokeCap.Round)
            )

            val bottomRightSide = Path().apply {
                moveTo(itemWidth, itemHeight)
                lineTo(
                    itemWidth - thirdWidth,
                    itemHeight
                ) // Draw line from bottom right to a third of the right side
                arcTo(
                    rect = Rect(
                        left = itemWidth - 2 * cornerRadius,
                        top = itemHeight - 2 * cornerRadius,
                        right = itemWidth,
                        bottom = itemHeight
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = -90f,
                    forceMoveTo = false
                )
                moveTo(itemWidth, itemHeight)
                lineTo(
                    itemWidth,
                    itemHeight - thirdHeight
                ) // Draw line from the bottom right towards the middle of bottom side


            }

            drawPath(
                path = bottomRightSide,
                color = rightSideColor,
                style = Stroke(width = frameThickness, cap = StrokeCap.Round)
            )
        }

        if (imageUrl == null) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(frameSize - 10.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(SparkyTheme.colors.white)
            ) {
                Text(
                    text = userFullName.getInitials(),
                    style = SparkyTheme.typography.poppinsSemiBold9,
                    color = SparkyTheme.colors.primaryColor
                )
            }
        } else AsyncImage(
            model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(frameSize - 5.dp)
                .clip(RoundedCornerShape(7.dp))
        )
    }
}
