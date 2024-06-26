package ltd.bokadev.sparky_social_media.core.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ltd.bokadev.sparky_social_media.core.navigation.NavType
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.ROOT
import ltd.bokadev.sparky_social_media.core.navigation.Routes.SPLASH
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import timber.log.Timber

@Composable
fun SparkyNavigation(
    navController: NavHostController, navigator: Navigator, showSnackBar: (message: String) -> Unit
) {
    navigator.navigationFlow.observeWithLifecycle { navType ->
        Timber.e("navType $navType")
        navigate(
            navController = navController, navType = navType
        )
    }
    NavHost(
        navController = navController, route = ROOT, startDestination = SPLASH
    ) {
        splashNavGraph(navController = navController, showSnackBar = showSnackBar)
        authNavGraph(navController = navController, showSnackBar = showSnackBar)
    }
}

private fun navigate(
    navController: NavHostController, navType: NavType
) {
    when (navType) {
        is NavType.NavigateToRoute -> {
            navController.navigate(navType.route)
        }

        is NavType.PopToRoute -> {
            navController.navigate(navType.route) {
                popUpTo(navType.staticRoute) {
                    inclusive = navType.inclusive
                }
            }
        }

        is NavType.PopBackStack -> {
            if (navType.route != null && navType.inclusive != null) navController.popBackStack(
                route = navType.route, inclusive = navType.inclusive
            )
            else navController.popBackStack()
        }

        is NavType.NavigateUp -> {
            navController.navigateUp()
        }
    }
}
