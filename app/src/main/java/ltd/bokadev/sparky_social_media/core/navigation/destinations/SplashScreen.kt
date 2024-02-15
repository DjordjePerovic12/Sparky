package ltd.bokadev.sparky_social_media.core.navigation.destinations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants.ANIMATION_DURATION
import ltd.bokadev.sparky_social_media.presentation.splash_screen.SplashScreen
import ltd.bokadev.sparky_social_media.presentation.splash_screen.SplashScreenViewModel

fun NavGraphBuilder.splashScreenComposable(
    navController: NavController,
    showSnackBar: (message: String) -> Unit
) {
    composable(
        route = Screen.SplashScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down
            )
        }
    ) { navBackStackEntry ->
        val parentEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(Routes.SPLASH)
        }
        val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>(parentEntry)
        SplashScreen(
            viewModel = splashScreenViewModel
        )

    }
}