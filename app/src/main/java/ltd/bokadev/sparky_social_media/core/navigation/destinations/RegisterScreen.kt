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
import ltd.bokadev.sparky_social_media.presentation.register_screen.RegisterScreen
import ltd.bokadev.sparky_social_media.presentation.register_screen.RegisterViewModel

fun NavGraphBuilder.registerScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.RegisterScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(animationSpec = tween(ANIMATION_DURATION))
        }) {
        val registerViewModel = hiltViewModel<RegisterViewModel>()
        RegisterScreen(viewModel = registerViewModel, showSnackBar = showSnackBar)
    }
}