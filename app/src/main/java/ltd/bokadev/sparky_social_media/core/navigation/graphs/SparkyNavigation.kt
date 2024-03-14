package ltd.bokadev.sparky_social_media.core.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ltd.bokadev.sparky_social_media.core.navigation.NavType
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.AUTH
import ltd.bokadev.sparky_social_media.core.navigation.Routes.HOME_SCREEN
import ltd.bokadev.sparky_social_media.core.navigation.Routes.ROOT
import ltd.bokadev.sparky_social_media.core.navigation.destinations.homeScreenComposable
import ltd.bokadev.sparky_social_media.core.navigation.destinations.notificationsScreenComposable
import ltd.bokadev.sparky_social_media.core.navigation.destinations.profileScreenComposable
import ltd.bokadev.sparky_social_media.core.navigation.destinations.searchScreenComposable
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import timber.log.Timber

@Composable
fun SparkyNavigation(
    navController: NavHostController,
    isLoggedIn: Boolean,
    navigator: Navigator,
    showSnackBar: (message: String) -> Unit,
    modifier: Modifier = Modifier
) {
    navigator.navigationFlow.observeWithLifecycle { navType ->
        Timber.e("navType $navType")
        navigate(
            navController = navController, navType = navType
        )
    }
    NavHost(
        navController = navController,
        route = ROOT,
        startDestination = if (isLoggedIn) HOME_SCREEN else AUTH,
        modifier = modifier
    ) {
        authNavGraph(navController = navController, showSnackBar = showSnackBar)
        homeScreenComposable(navController = navController, showSnackBar = showSnackBar)
        searchScreenComposable(navController = navController, showSnackBar = showSnackBar)
        profileScreenComposable(navController = navController, showSnackBar = showSnackBar)
        notificationsScreenComposable(navController = navController, showSnackBar = showSnackBar)
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
