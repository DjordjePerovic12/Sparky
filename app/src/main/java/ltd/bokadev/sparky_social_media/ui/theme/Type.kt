package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import javax.annotation.concurrent.Immutable

@Immutable
data class SparkyTypography(
    val poppinsRegular12: TextStyle,
    val poppinsRegular14: TextStyle,
    val poppinsRegular16: TextStyle,
    val poppinsRegular20: TextStyle,
    val poppinsMedium12: TextStyle,
    val poppinsMedium14: TextStyle,
    val poppinsMedium16: TextStyle,
    val poppinsMedium24: TextStyle,
    val poppinsMedium32: TextStyle,
    val poppinsSemiBold9: TextStyle,
    val poppinsSemiBold16: TextStyle,
    val poppinsSemiBold24: TextStyle
)

val LocalSparkyTypography = staticCompositionLocalOf {
    SparkyTypography(
        poppinsRegular12 = TextStyle.Default,
        poppinsRegular14 = TextStyle.Default,
        poppinsRegular16 = TextStyle.Default,
        poppinsRegular20 = TextStyle.Default,
        poppinsMedium12 = TextStyle.Default,
        poppinsMedium14 = TextStyle.Default,
        poppinsMedium16 = TextStyle.Default,
        poppinsMedium24 = TextStyle.Default,
        poppinsMedium32 = TextStyle.Default,
        poppinsSemiBold9 = TextStyle.Default,
        poppinsSemiBold16 = TextStyle.Default,
        poppinsSemiBold24 = TextStyle.Default,
    )
}

val sparkyTypography = SparkyTypography(
    poppinsRegular12 = TextStyle(
        fontSize = 12.sp, lineHeight = 16.sp
    ),
    poppinsRegular14 = TextStyle(
        fontSize = 14.sp, lineHeight = 20.sp
    ),
    poppinsRegular16 = TextStyle(
        fontSize = 16.sp, lineHeight = 24.sp
    ),
    poppinsRegular20 = TextStyle(
        fontSize = 20.sp
    ), poppinsMedium12 = TextStyle(
        fontSize = 12.sp, lineHeight = 16.sp
    ), poppinsMedium14 = TextStyle(
        fontSize = 14.sp, lineHeight = 20.sp
    ), poppinsMedium16 = TextStyle(
        fontSize = 16.sp, lineHeight = 24.sp
    ), poppinsMedium24 = TextStyle(
        fontSize = 24.sp, lineHeight = 32.sp
    ), poppinsMedium32 = TextStyle(
        fontSize = 32.sp, lineHeight = 40.sp
    ),
    poppinsSemiBold9 = TextStyle(
        fontSize = 9.sp,
        lineHeight = 13.sp
    ),
    poppinsSemiBold16 = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    poppinsSemiBold24 = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
)