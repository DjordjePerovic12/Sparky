package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper
import ltd.bokadev.sparky_social_media.presentation.utils.formatDate
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun NotificationsScreenContent(
    notifications: LazyPagingItems<NotificationWrapper>
) {
    val notificationsByDay = notifications.itemSnapshotList.sortedBy { it?.createAt }
        .groupBy { it?.createAt?.formatDate() }.entries.toList()
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
                items(notificationsByDay) { (date, notifications) ->
                    if (date != null) {
                        Text(
                            text = date,
                            style = SparkyTheme.typography.poppinsSemiBold16,
                            color = SparkyTheme.colors.black
                        )
                    }
                    notifications.forEach { notification ->
                        if (notification != null) {
                            NotificationItem(notifications = notification)
                        }
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