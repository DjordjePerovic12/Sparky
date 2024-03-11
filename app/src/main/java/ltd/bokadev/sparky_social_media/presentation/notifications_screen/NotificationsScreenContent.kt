package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper

@Composable
fun NotificationsScreenContent(
    notifications: LazyPagingItems<NotificationWrapper>
) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(topBar = {
            NotificationScreenTopBar() {

            }
        }) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .padding(top = innerPadding.calculateTopPadding())
                    .padding(horizontal = 20.dp)
            ) {
                items(notifications.itemCount) { index ->
                    val notification = notifications[index]
                    if (notification != null) {
                        NotificationItem(notifications = notification)
                    }
                }
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun NotificationsPreview() {
//    NotificationsScreenContent(notifications = emptyPag())
//}