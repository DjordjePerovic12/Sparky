package ltd.bokadev.sparky_social_media.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

data class FollowButtonStyle(
    val containerColor: Color,
    val borderColor: Color,
    val content: Color,
    val sizeModifier: Modifier,
    val label: String
)
