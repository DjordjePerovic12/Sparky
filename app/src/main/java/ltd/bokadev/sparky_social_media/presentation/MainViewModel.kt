package ltd.bokadev.sparky_social_media.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants.MAX_POST_LENGTH
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.core.utils.isToken
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreenState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SparkyRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    private val _isCheckingSessionFinished = MutableStateFlow(true)
    val isCheckingSessionFinished = _isCheckingSessionFinished.asStateFlow()

    init {
        viewModelScope.launch {
            checkSession()
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnCreatePostClick -> {
                state = state.copy(shouldShowCircularProgressIndicator = true)
                executeCreatePost()
            }

            is MainEvent.OnMessageChange -> {
                state =
                    if (event.message.isNotEmpty()) state.copy(shouldEnableCreateButton = true) else state.copy(
                        shouldEnableCreateButton = false
                    )
                if (event.message.length <= MAX_POST_LENGTH) state =
                    state.copy(message = event.message)
            }
        }
    }

    private fun executeCreatePost() {
        viewModelScope.launch {
            when(repository.createPost(PostRequest(state.message))) {
                    is Resource.Success -> {
                        state = state.copy(
                            shouldShowCircularProgressIndicator = false,
                            shouldCloseBottomSheet = true,
                            message = String()
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(shouldShowCircularProgressIndicator = false)
                        _snackBarChannel.send("Error posting comment")
                    }

                    else -> {}
                }
        }
    }

    fun isLoggedIn() = runBlocking {
        withContext(Dispatchers.IO) {
            Timber.e("TOKEN ${dataStoreRepository.getToken().first()}")
            dataStoreRepository.getToken().first().isToken()
        }
    }

    private fun checkSession() {
        try {
            isLoggedIn()
        } finally {
            _isCheckingSessionFinished.value = false
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
    val shouldCloseBottomSheet: Boolean = false,
    val isLoggedIn: Boolean? = null
)