package ltd.bokadev.sparky_social_media.core.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.navigation.destinations.loginScreenComposable
import ltd.bokadev.sparky_social_media.core.navigation.destinations.registerScreenComposable
import ltd.bokadev.sparky_social_media.core.navigation.destinations.welcomeScreenComposable

fun NavGraphBuilder.authNavGraph(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    navigation(
        route = Routes.AUTH, startDestination = Screen.WelcomeScreen.route
    ) {
        welcomeScreenComposable(navController = navController, showSnackBar = showSnackBar)
        loginScreenComposable(navController = navController, showSnackBar = showSnackBar)
        registerScreenComposable(navController = navController, showSnackBar = showSnackBar)
    }
}