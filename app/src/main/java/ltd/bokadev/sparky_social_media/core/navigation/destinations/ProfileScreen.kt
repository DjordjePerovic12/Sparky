package ltd.bokadev.sparky_social_media.core.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileScreen
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileViewModel

fun NavGraphBuilder.profileScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.ProfileScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION))
        }) {
        val profileViewModel = hiltViewModel<ProfileViewModel>()
        ProfileScreen(
            viewModel = profileViewModel, showSnackBar = showSnackBar
        )
    }
}