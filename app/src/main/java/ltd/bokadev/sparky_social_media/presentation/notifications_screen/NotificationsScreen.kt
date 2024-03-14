package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import ltd.bokadev.sparky_social_media.core.navigation.Screen

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel,
    navController: NavController,
    showSnackBar: (message: String) -> Unit
) {
    val notifications = viewModel.executeGetNotifications().collectAsLazyPagingItems()
    NotificationsScreenContent(notifications = notifications,
        onBackClick = {
            viewModel.onEvent(NotificationsScreenEvent.OnBackClick)
        }, onUserImageClick = {
            navController.navigate(Screen.ProfileScreen.passUserId(it.id))
        })
}