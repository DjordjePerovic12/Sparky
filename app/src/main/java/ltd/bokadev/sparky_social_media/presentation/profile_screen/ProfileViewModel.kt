package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.search_screen.SearchState
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigator: Navigator, private val repository: SparkyRepository
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set


    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    init {
        executeGetUser()
    }


    private fun executeGetUser() {
        viewModelScope.launch {
            state = state.copy(isLoadingUserData = true)
            repository.getProfileDetails(null)
                .collectLatestWithAuthCheck(navigator = navigator, onSuccess = { result ->
                    result.data.let { user ->
                        state = state.copy(user = user, isLoadingUserData = false)
                    }
                }, onError = {
                    state = state.copy(isLoadingUserData = false)
                    _snackBarChannel.send("Error fetching user data")
                })
        }
    }


}

sealed class ProfileEvent {

}


data class ProfileState(
    val user: User? = null, val isLoadingUserData: Boolean = false
)