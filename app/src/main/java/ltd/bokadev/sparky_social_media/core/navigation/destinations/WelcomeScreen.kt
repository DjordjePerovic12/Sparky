package ltd.bokadev.sparky_social_media.core.navigation.destinations

import android.view.FrameMetrics
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
import ltd.bokadev.sparky_social_media.presentation.splash_screen.SplashScreen
import ltd.bokadev.sparky_social_media.presentation.splash_screen.SplashScreenViewModel
import ltd.bokadev.sparky_social_media.presentation.welcome_screen.WelcomeScreen

fun NavGraphBuilder.welcomeScreenComposable(
    navController: NavController,
    showSnackBar: (message: String) -> Unit
) {
    composable(
        route = Screen.WelcomeScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(FrameMetrics.ANIMATION_DURATION)) },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down
            )
        }
    ) { navBackStackEntry ->
//        val parentEntry = remember(navBackStackEntry) {
//            navController.getBackStackEntry(Routes.WELCOME)
//        }
//        val welcomeViewModel = hiltViewModel<WelcomeScreenViewModel>(parentEntry)
        WelcomeScreen()

    }
}