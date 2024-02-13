package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun SparkyAppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSparkyColors provides sparkyColors,
        LocalSparkyTypography provides sparkyTypography,
        content = content
    )
}

object SparkyTheme {
    val colors: SparkyColors
        @Composable get() = LocalSparkyColors.current
    val typography: SparkyTypography
        @Composable get() = LocalSparkyTypography.current
}