package ltd.bokadev.sparky_social_media.core.navigation

import ltd.bokadev.sparky_social_media.core.navigation.Routes.HOME_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.LOGIN_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.NOTIFICATIONS_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.PROFILE_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.REGISTER_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.REMOTE_USER_PROFILE_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.SEARCH_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.WELCOME_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.destinations.USER_ID_ARGUMENT_KEY

sealed class Screen(val route: String) {
    data object WelcomeScreen : Screen(WELCOME_SCREEN)
    data object LoginScreen : Screen(LOGIN_SCREEN)
    data object RegisterScreen : Screen(REGISTER_SCREEN)
    data object HomeScreen : Screen(HOME_SCREEN)
    data object SearchScreen : Screen(SEARCH_SCREEN)
    data object ProfileScreen : Screen(PROFILE_SCREEN)
    data object RemoteUserProfileScreen : Screen(REMOTE_USER_PROFILE_SCREEN) {
        fun passUserId(userId: String) = this.route.replace(
            oldValue = "{$USER_ID_ARGUMENT_KEY}",
            newValue = userId
        )
    }

    data object NotificationsScreen : Screen(NOTIFICATIONS_SCREEN)
}