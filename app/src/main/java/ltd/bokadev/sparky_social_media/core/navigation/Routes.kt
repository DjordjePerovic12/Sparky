package ltd.bokadev.sparky_social_media.core.navigation

import ltd.bokadev.sparky_social_media.core.navigation.destinations.USER_ID_ARGUMENT_KEY

object Routes {
    const val ROOT = "root"
    const val AUTH = "auth"
    const val WELCOME_SCREEN = "welcome_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val REGISTER_SCREEN = "register_screen"
    const val HOME_SCREEN = "home_screen"
    const val SEARCH_SCREEN = "search_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val REMOTE_USER_PROFILE_SCREEN = "remote_user_profile_screen/{$USER_ID_ARGUMENT_KEY}"
    const val NOTIFICATIONS_SCREEN = "notifications_screen"
}