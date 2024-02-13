package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val PrimaryColor = Color(0xFF222222)


@Immutable
data class SparkyColors(
    val black: Color,
    val white: Color,
    val primaryColor: Color
)

val LocalSparkyColors = staticCompositionLocalOf {
    SparkyColors(
        black = Color.Unspecified,
        white = Color.Unspecified,
        primaryColor = Color.Unspecified
    )
}

val sparkyColors = SparkyColors(
    black = Black,
    white = White,
    primaryColor = PrimaryColor
)