package ltd.bokadev.sparky_social_media.presentation

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
import ltd.bokadev.sparky_social_media.core.utils.Constants.MAX_POST_LENGTH
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreenState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SparkyRepository, private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnCreatePostClick -> {
                state = state.copy(shouldShowCircularProgressIndicator = true)
                executeCreatePost()
            }

            is MainEvent.OnMessageChange -> {
                state =
                    if (event.message.isNotEmpty()) state.copy(shouldEnableCreateButton = true) else state.copy(shouldEnableCreateButton = false)
                if (event.message.length <= MAX_POST_LENGTH) state =
                    state.copy(message = event.message)
            }
        }
    }

    private fun executeCreatePost() {
        viewModelScope.launch {
            repository.createPost(PostRequest(state.message))
                .collectLatestWithAuthCheck(navigator = navigator, onSuccess = {
                    state = state.copy(
                        shouldShowCircularProgressIndicator = false,
                        shouldCloseBottomSheet = true,
                        message = String()
                    )
                }, onError = {
                    state = state.copy(shouldShowCircularProgressIndicator = false)
                    _snackBarChannel.send("Error posting comment")
                })
        }
    }


    //This is done so that when a post is once successfully added,
    // we return the flag that handles closing the sheet after successfully creation to false
    //so it will always close the sheet when a post is added successfully
    fun resetShouldCloseBottomSheet() {
        state = state.copy(shouldCloseBottomSheet = false)
    }


    //This is done so that the message doesn't appear in the text field when open after the user closed it willingly
    //Not sure if should call this when bottom sheet is dismissed other way then by clicking on close icon
    //because maybe it could have been an accidental dismiss and the user could "lose" his post
    fun resetMessage() {
        state = state.copy(message = String())
    }

}

sealed class MainEvent {
    data object OnCreatePostClick : MainEvent()
    data class OnMessageChange(val message: String) : MainEvent()
}

data class MainState(
    val message: String = String(),
    val shouldShowCircularProgressIndicator: Boolean = false,
    val shouldEnableCreateButton: Boolean = false,
    val shouldCloseBottomSheet: Boolean = false
)