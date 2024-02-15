package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

val Black = Color(0xFF000000)
val White = Color(0xFFFAFAFA)
val PrimaryColor = Color(0xFF222222)
val TextSecondaryColor = Color(0xFF9CA3AF)
val Yellow = Color(0xFFFECE00)


@Immutable
data class SparkyColors(
    val black: Color,
    val white: Color,
    val primaryColor: Color,
    val textSecondaryColor: Color,
    val yellow: Color
)

val LocalSparkyColors = staticCompositionLocalOf {
    SparkyColors(
        black = Color.Unspecified,
        white = Color.Unspecified,
        primaryColor = Color.Unspecified,
        textSecondaryColor = Color.Unspecified,
        yellow = Color.Unspecified
    )
}

val sparkyColors = SparkyColors(
    black = Black,
    white = White,
    primaryColor = PrimaryColor,
    textSecondaryColor = TextSecondaryColor,
    yellow = Yellow
)