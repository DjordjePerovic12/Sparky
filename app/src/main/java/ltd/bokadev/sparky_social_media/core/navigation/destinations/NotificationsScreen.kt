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
import ltd.bokadev.sparky_social_media.presentation.login_screen.LoginScreen
import ltd.bokadev.sparky_social_media.presentation.login_screen.LoginViewModel
import ltd.bokadev.sparky_social_media.presentation.notifications_screen.NotificationsScreen
import ltd.bokadev.sparky_social_media.presentation.notifications_screen.NotificationsViewModel

fun NavGraphBuilder.notificationsScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.NotificationsScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION))
        }) {
        val notificationsViewModel = hiltViewModel<NotificationsViewModel>()
        NotificationsScreen(
            viewModel = notificationsViewModel,
            navController = navController,
            showSnackBar = showSnackBar
        )
    }
}