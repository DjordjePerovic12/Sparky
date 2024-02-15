package ltd.bokadev.sparky_social_media.core.navigation.destinations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants.ANIMATION_DURATION
import ltd.bokadev.sparky_social_media.presentation.login_screen.LoginScreen

fun NavGraphBuilder.loginScreenComposable(
    navController: NavController, showSnackBar: (message: String) -> Unit
) {
    composable(route = Screen.LoginScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down
            )
        }) { navBackStackEntry ->
        val parentEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(Routes.AUTH)
        }
//        val loginViewModel = hiltViewModel<LoginViewModel>(parentEntry)
        LoginScreen()
    }
}