package ltd.bokadev.sparky_social_media.core.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants.ANIMATION_DURATION
import ltd.bokadev.sparky_social_media.presentation.welcome_screen.WelcomeScreen
import ltd.bokadev.sparky_social_media.presentation.welcome_screen.WelcomeScreenViewModel

fun NavGraphBuilder.welcomeScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.WelcomeScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(
                animationSpec = tween(ANIMATION_DURATION)
            )
        }) { navBackStackEntry ->
        val parentEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(Routes.AUTH)
        }
        val welcomeViewModel = hiltViewModel<WelcomeScreenViewModel>(parentEntry)
        WelcomeScreen(
            viewModel = welcomeViewModel
        )
    }
}