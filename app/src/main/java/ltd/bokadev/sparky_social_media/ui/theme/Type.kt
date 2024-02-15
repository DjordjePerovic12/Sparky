package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import javax.annotation.concurrent.Immutable

@Immutable
data class SparkyTypography(
    val poppinsRegular14: TextStyle,
    val poppinsRegular20: TextStyle,
    val poppinsMedium16: TextStyle,
    val poppinsMedium24: TextStyle,
    val poppinsMedium32: TextStyle
)

val LocalSparkyTypography = staticCompositionLocalOf {
    SparkyTypography(
        poppinsRegular14 = TextStyle.Default,
        poppinsRegular20 = TextStyle.Default,
        poppinsMedium16 = TextStyle.Default,
        poppinsMedium24 = TextStyle.Default,
        poppinsMedium32 = TextStyle.Default
    )
}

val sparkyTypography = SparkyTypography(
    poppinsRegular14 = TextStyle(
        fontSize = 14.sp, lineHeight = 20.sp
    ), poppinsRegular20 = TextStyle(
        fontSize = 20.sp
    ), poppinsMedium16 = TextStyle(
        fontSize = 16.sp, lineHeight = 24.sp
    ), poppinsMedium24 = TextStyle(
        fontSize = 24.sp, lineHeight = 32.sp
    ), poppinsMedium32 = TextStyle(
        fontSize = 32.sp, lineHeight = 40.sp
    )
)