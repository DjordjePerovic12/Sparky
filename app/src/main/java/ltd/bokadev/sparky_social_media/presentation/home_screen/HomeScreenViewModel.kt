package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.login_screen.LoginState
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: SparkyRepository,
    private val navigator: Navigator
) : ViewModel() {

    //TODO: Implement home screen viewModel

    var state by mutableStateOf(HomeScreenState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()


    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnCreatePostClick -> {

            }
        }
    }


}


sealed class HomeScreenEvent {
    data class OnCreatePostClick(val content: String) : HomeScreenEvent()
}

data class HomeScreenState(
    val postContent: String = String(),
)