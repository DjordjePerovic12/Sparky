package ltd.bokadev.sparky_social_media.presentation.notifications_screen

import androidx.lifecycle.ViewModel
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import javax.inject.Inject

class NotificationsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val repository: SparkyRepository
): ViewModel() {
}