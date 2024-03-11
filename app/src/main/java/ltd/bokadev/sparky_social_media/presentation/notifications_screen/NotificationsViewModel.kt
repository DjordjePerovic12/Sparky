package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper
import ltd.bokadev.sparky_social_media.domain.model.Notifications
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val repository: SparkyRepository
) : ViewModel() {

    fun executeGetNotifications(): Flow<PagingData<NotificationWrapper>> {
        return repository.getNotifications(pageCount = 20)
    }
}