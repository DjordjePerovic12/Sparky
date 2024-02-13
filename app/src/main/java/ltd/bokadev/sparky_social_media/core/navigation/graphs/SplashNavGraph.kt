package ltd.bokadev.sparky_social_media.core.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.navigation.destinations.splashScreenComposable

fun NavGraphBuilder.splashNavGraph(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    navigation(
        route = Routes.SPLASH, startDestination = Screen.SplashScreen.route
    ) {
        splashScreenComposable(
            navController = navController, showSnackBar = showSnackBar
        )
    }
}
