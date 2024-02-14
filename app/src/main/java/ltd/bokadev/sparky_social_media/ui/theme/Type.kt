package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import javax.annotation.concurrent.Immutable

@Immutable
data class SparkyTypography(
    val poppinsRegular20: TextStyle, val poppinsMedium32: TextStyle
)

val LocalSparkyTypography = staticCompositionLocalOf {
    SparkyTypography(
        poppinsRegular20 = TextStyle.Default, poppinsMedium32 = TextStyle.Default
    )
}

val sparkyTypography = SparkyTypography(
    poppinsRegular20 = TextStyle(
        fontSize = 20.sp
    ),
    poppinsMedium32 = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp
    )
)