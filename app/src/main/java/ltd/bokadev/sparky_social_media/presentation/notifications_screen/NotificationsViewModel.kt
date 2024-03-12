package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val navigator: Navigator, private val repository: SparkyRepository
) : ViewModel() {

    fun onEvent(event: NotificationsScreenEvent) {
        when (event) {
            is NotificationsScreenEvent.OnUserImageClick -> {
                navigateToUserProfileScreen(event.user.id)
            }

            is NotificationsScreenEvent.OnBackClick -> {
                navigateBack()
            }
        }
    }

    fun executeGetNotifications(): Flow<PagingData<NotificationWrapper>> {
        return repository.getNotifications(pageCount = 20)
    }

    private fun navigateToUserProfileScreen(userId: String) {
        viewModelScope.launch {
            navigator.navigateTo(Screen.RemoteUserProfileScreen.passUserId(userId))
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            navigator.popBackStack()
        }
    }
}

sealed class NotificationsScreenEvent {
    data class OnUserImageClick(val user: UserDetails) : NotificationsScreenEvent()
    data object OnBackClick : NotificationsScreenEvent()
}