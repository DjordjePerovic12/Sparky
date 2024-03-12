package ltd.bokadev.sparky_social_media.core.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileEvent
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileViewModel
import ltd.bokadev.sparky_social_media.presentation.remote_user_profile_screen.RemoteUserProfileScreen
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentsViewModel


const val USER_ID_ARGUMENT_KEY = "user_id"

fun NavGraphBuilder.remoteUserProfileScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.RemoteUserProfileScreen.route,
        arguments = listOf(
            navArgument(USER_ID_ARGUMENT_KEY) {
                type = NavType.StringType
            },
        ),
        enterTransition = { fadeIn(animationSpec = tween(Constants.ANIMATION_DURATION)) },
        exitTransition = {
            fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION))
        }) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Routes.ROOT)
        }
        val profileViewModel = hiltViewModel<ProfileViewModel>(parentEntry)
        val commentViewModel = hiltViewModel<CommentsViewModel>(parentEntry)
        val userId = backStackEntry.arguments?.getString(USER_ID_ARGUMENT_KEY) ?: ""
        RemoteUserProfileScreen(
            userId = userId,
            profileViewModel = profileViewModel,
            commentsViewModel = commentViewModel,
            showSnackBar = showSnackBar
        )
    }
}