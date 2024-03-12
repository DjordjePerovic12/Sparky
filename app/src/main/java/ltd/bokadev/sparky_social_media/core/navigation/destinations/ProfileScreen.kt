package ltd.bokadev.sparky_social_media.core.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ltd.bokadev.sparky_social_media.core.navigation.Routes.ROOT
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileScreen
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileViewModel
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentsViewModel

fun NavGraphBuilder.profileScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.ProfileScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION))
        }) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(ROOT)
        }
        val profileViewModel = hiltViewModel<ProfileViewModel>(parentEntry)
        val commentViewModel = hiltViewModel<CommentsViewModel>(parentEntry)
        ProfileScreen(
            profileViewModel = profileViewModel,
            commentsViewModel = commentViewModel,
            showSnackBar = showSnackBar
        )
    }
}