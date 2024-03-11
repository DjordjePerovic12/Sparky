package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NotificationsScreenContent() {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(topBar = {
            NotificationScreenTopBar() {

            }
        }) { innerPadding ->

        }
    }
}


@Preview
@Composable
fun NotificationsPreview() {
    NotificationsScreenContent()
}