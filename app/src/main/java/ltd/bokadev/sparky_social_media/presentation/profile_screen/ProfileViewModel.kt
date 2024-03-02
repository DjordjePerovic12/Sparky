package ltd.bokadev.sparky_social_media.presentation.profile_screen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.AUTH
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.domain.utils.getImage
import ltd.bokadev.sparky_social_media.presentation.search_screen.SearchState
import okhttp3.MultipartBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val navigator: Navigator,
    private val dataStoreRepository: DataStoreRepository,
    private val repository: SparkyRepository
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set


    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    init {
        executeGetProfilePosts()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnLogoutClick -> {
                state = state.copy(shouldShowDialog = true)
            }

            is ProfileEvent.OnConfirmClick -> {
                executeLogout()
            }

            is ProfileEvent.OnCloseClick -> {
                state = state.copy(shouldShowDialog = false)
            }

            is ProfileEvent.OnPostFilterClick -> {
                state = state.copy(selectedFilter = event.selectedFilterId)
            }

            is ProfileEvent.ImageSelected -> {
                event.image?.let { executeChangeProfilePicture(it) }
            }
        }
    }

    init {
        executeGetUser()
    }


    private fun executeGetUser() {
        viewModelScope.launch {
            state = state.copy(isLoadingUserData = true)
            repository.getProfileDetails(null)
                .collectLatestWithAuthCheck(navigator = navigator, onSuccess = { result ->
                    result.data.let { user ->
                        state = state.copy(user = user?.user, isLoadingUserData = false)
                    }
                }, onError = {
                    state = state.copy(isLoadingUserData = false)
                    _snackBarChannel.send("Error fetching user data")
                })
        }
    }

    private fun executeLogout() {
        viewModelScope.launch {
            repository.logout().collectLatestWithAuthCheck(navigator = navigator, onSuccess = {
                clearDatastore()
                navigateToHomeScreen()
            }, onError = {
                _snackBarChannel.send("Error logging out")
            })
        }
    }

    private fun navigateToHomeScreen() {
        viewModelScope.launch {
            navigator.popUpTo(
                route = Screen.LoginScreen.route, staticRoute = AUTH, inclusive = true
            )
        }
    }

    private fun clearDatastore() = runBlocking {
        withContext(Dispatchers.IO) {
            dataStoreRepository.clearDatastore()
        }
    }

    private fun executeChangeProfilePicture(profilePicture: MultipartBody.Part) {
        viewModelScope.launch {
            when (val result = repository.changeProfilePicture(profilePicture)) {
                is Resource.Success -> {
                    Timber.e("RESULT DATA ${result.data}")
                    result.data.let {
                        Timber.e("RESULT LET")
                        state = state.copy(user = it)
                    }
                    _snackBarChannel.send("Successfully updated profile picture")
                }

                is Resource.Error -> {
                    _snackBarChannel.send(result.message.toString())
                }

                is Resource.Loading -> {
                    _snackBarChannel.send("LOADING")
                    Timber.e("LOADING STATE ")
                }

                else -> {}
            }
        }
    }

    private fun executeGetProfilePosts() {
        viewModelScope.launch {
            val result = repository.getProfilePosts(null, pageCount = 20)
            result.let {
                state = state.copy(userPosts = it)
            }
        }
    }


}

sealed class ProfileEvent {
    data object OnLogoutClick : ProfileEvent()
    data object OnConfirmClick : ProfileEvent()
    data object OnCloseClick : ProfileEvent()
    data class OnPostFilterClick(val selectedFilterId: Int) : ProfileEvent()
    data class ImageSelected(val image: MultipartBody.Part?) : ProfileEvent()
}


data class ProfileState(
    val user: UserDetails? = null,
    val isLoadingUserData: Boolean = false,
    val shouldShowDialog: Boolean = false,
    val selectedFilter: Int = 0,
    val selectedImage: Uri? = null,
    val userPosts: List<Post> = emptyList()
)