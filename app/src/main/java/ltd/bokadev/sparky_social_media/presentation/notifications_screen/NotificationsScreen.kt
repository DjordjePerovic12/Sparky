package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel,
    showSnackBar: (message: String) -> Unit
) {
    val notifications = viewModel.executeGetNotifications().collectAsLazyPagingItems()
    NotificationsScreenContent(notifications = notifications,
        onBackClick = {
            viewModel.onEvent(NotificationsScreenEvent.OnBackClick)
        }, onUserImageClick = {
            viewModel.onEvent(NotificationsScreenEvent.OnUserImageClick(it))
        })
}