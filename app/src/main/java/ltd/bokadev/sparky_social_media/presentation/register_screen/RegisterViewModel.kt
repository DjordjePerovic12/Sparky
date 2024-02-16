package ltd.bokadev.sparky_social_media.presentation.register_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sparkyRepository: SparkyRepository, private val navigator: Navigator
) : ViewModel() {

}


sealed class RegisterEvent {
    data class OnRegisterClick(
        val userName: String,
        val email: String,
        val password: String
    )
}

