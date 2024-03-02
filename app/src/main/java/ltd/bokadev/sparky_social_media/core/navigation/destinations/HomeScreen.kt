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
import ltd.bokadev.sparky_social_media.core.utils.Constants
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreen
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreenViewModel
import ltd.bokadev.sparky_social_media.presentation.register_screen.RegisterScreen
import ltd.bokadev.sparky_social_media.presentation.register_screen.RegisterViewModel

fun NavGraphBuilder.homeScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.HomeScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION))
        }) {
        val homeViewModel = hiltViewModel<HomeScreenViewModel>()
        HomeScreen(viewModel = homeViewModel, showSnackBar = showSnackBar)
    }
}