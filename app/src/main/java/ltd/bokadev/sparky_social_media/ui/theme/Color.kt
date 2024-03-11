package ltd.bokadev.sparky_social_media.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable

val Black = Color(0xFF000000)
val White = Color(0xFFFAFAFA)
val PrimaryColor = Color(0xFF222222)
val TextSecondaryColor = Color(0xFF9CA3AF)
val Yellow = Color(0xFFFECE00)
val TextLightGray = Color(0xFF6B7280)
val LightGray = Color(0xFF3B3A42)
val UserDataTextField = Color(0xFFF9FAFB)
val BottomNavigationUnselected = Color(0xFF676D75)
val Red = Color(0xFFBB3D3D)
val NotificationHourColor = Color(0xFFAAAAAA)

@Immutable
data class SparkyColors(
    val black: Color,
    val white: Color,
    val primaryColor: Color,
    val textSecondaryColor: Color,
    val yellow: Color,
    val textLightGray: Color,
    val userDataTextField: Color,
    val bottomNavigationUnselected: Color,
    val lightGray: Color,
    val red: Color,
    val notificationHourColor: Color
)

val LocalSparkyColors = staticCompositionLocalOf {
    SparkyColors(
        black = Color.Unspecified,
        white = Color.Unspecified,
        primaryColor = Color.Unspecified,
        textSecondaryColor = Color.Unspecified,
        yellow = Color.Unspecified,
        textLightGray = Color.Unspecified,
        userDataTextField = Color.Unspecified,
        bottomNavigationUnselected = Color.Unspecified,
        lightGray = Color.Unspecified,
        red = Color.Unspecified,
        notificationHourColor = Color.Unspecified
    )
}

val sparkyColors = SparkyColors(
    black = Black,
    white = White,
    primaryColor = PrimaryColor,
    textSecondaryColor = TextSecondaryColor,
    yellow = Yellow,
    textLightGray = TextLightGray,
    userDataTextField = UserDataTextField,
    bottomNavigationUnselected = BottomNavigationUnselected,
    lightGray = LightGray,
    red = Red,
    notificationHourColor = NotificationHourColor
)