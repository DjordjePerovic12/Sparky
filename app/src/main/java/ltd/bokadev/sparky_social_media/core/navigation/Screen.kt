package ltd.bokadev.sparky_social_media.core.navigation

import ltd.bokadev.sparky_social_media.core.navigation.Routes.SPLASH_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.WELCOME_SCREEN

sealed class Screen(val route: String) {

    data object SplashScreen : Screen(SPLASH_SCREEN)
    data object WelcomeScreen : Screen(WELCOME_SCREEN)
}