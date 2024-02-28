package ltd.bokadev.sparky_social_media.core.navigation

import ltd.bokadev.sparky_social_media.core.navigation.Routes.HOME_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.LOGIN_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.PROFILE_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.REGISTER_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.SEARCH_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.SPLASH_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.WELCOME_SCREEN

sealed class Screen(val route: String) {

    data object SplashScreen : Screen(SPLASH_SCREEN)
    data object WelcomeScreen : Screen(WELCOME_SCREEN)
    data object LoginScreen : Screen(LOGIN_SCREEN)
    data object RegisterScreen : Screen(REGISTER_SCREEN)
    data object HomeScreen : Screen(HOME_SCREEN)
    data object SearchScreen : Screen(SEARCH_SCREEN)
    data object ProfileScreen : Screen(PROFILE_SCREEN)
}