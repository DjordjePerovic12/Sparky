package ltd.bokadev.sparky_social_media.presentation.profile_screen

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.AUTH
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreenEvent
import ltd.bokadev.sparky_social_media.ui.theme.sparkyColors
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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()


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

            is ProfileEvent.OnCommentsClick -> {
                state = state.copy(selectedPostId = event.postId)
                executeGetComments(event.postId)
            }

            is ProfileEvent.OnCommentChanged -> {
                if (event.comment.length <= 280) state = state.copy(comment = event.comment)
            }

            is ProfileEvent.OnAddCommentClick -> {
                state = state.copy(isLoading = true)
                executeAddComment()
            }
        }
    }

    init {
        executeGetUser()
        executeGetProfilePosts()
    }


    //You commented on if i Want to use the selected postId..
    //I use it so I can refresh the opened posts comments after adding a new one
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            executeGetComments(state.selectedPostId)
            executeGetProfilePosts()
            delay(500)
            _isRefreshing.emit(false)
        }
    }


    private fun executeGetUser() {
        viewModelScope.launch {
            state = state.copy(isLoadingUserData = true)
            when (val result = repository.getProfileDetails(null)) {
                is Resource.Success -> {
                    result.data.let { user ->
                        state = state.copy(user = user?.user, isLoadingUserData = false)
                    }
                }

                is Resource.Error -> {
                    state = state.copy(isLoadingUserData = false)
                    _snackBarChannel.send("Error fetching user data")
                }

                else -> {}
            }
        }
    }

    private fun executeLogout() {
        viewModelScope.launch {
            when (repository.logout()) {
                is Resource.Success -> {
                    clearDatastore()
                    navigateToHomeScreen()
                }

                is Resource.Error -> {
                    _snackBarChannel.send("Error logging out")
                }

                else -> {}
            }
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

    fun executeGetProfilePosts(): Flow<PagingData<Post>> {
        var result: Flow<PagingData<Post>> = flowOf()
        viewModelScope.launch {
            result = repository.getProfilePosts(
                null,
                pageCount = 20,
                isLiked = false
            )

        }
        return result
    }

    fun executeGetLikedPosts(): Flow<PagingData<Post>> {
        var result: Flow<PagingData<Post>> = flowOf()
        viewModelScope.launch {
            result = repository.getLikedPosts(
                null,
                pageCount = 20,
                isLiked = true
            )

        }
        return result
    }

    //Would it be a good idea to
    //make a VM regarding only the comment logic
    //and then share it on home and profile screen
    //I did it here because I wasn't sure
    //It is not that complicated logic, but the Profile VM
    //would be much cleaner

    private fun executeGetComments(postId: String) {
        viewModelScope.launch {
            when (val result = repository.getPostComments(postId)) {
                is Resource.Success -> {
                    result.data.let { comments ->
                        state = state.copy(comments = comments, isRefreshing = false)
                    }
                }

                is Resource.Error -> {
                    _snackBarChannel.send("Error fetching comments")
                }

                else -> {}
            }
        }
    }

    private fun executeAddComment() {
        viewModelScope.launch {
            val result = repository.addComment(
                CommentRequest(
                    postId = state.selectedPostId, content = state.comment
                )
            )
            when (result) {
                is Resource.Success -> {
                    state = state.copy(isLoading = false, comment = String())
                    refresh()
                }

                is Resource.Error -> {
                    state = state.copy(isLoading = false)
                    _snackBarChannel.send("Error posting comment")
                }

                else -> {}
            }
        }
    }

    fun getUserData() = runBlocking {
        withContext(Dispatchers.IO) {
            dataStoreRepository.getUser().first()
        }
    }


}

sealed class ProfileEvent {
    data object OnLogoutClick : ProfileEvent()
    data object OnConfirmClick : ProfileEvent()
    data object OnCloseClick : ProfileEvent()
    data class OnPostFilterClick(val selectedFilterId: Int) : ProfileEvent()
    data class ImageSelected(val image: MultipartBody.Part?) : ProfileEvent()
    data class OnCommentsClick(val postId: String) : ProfileEvent()
    data class OnCommentChanged(val comment: String) : ProfileEvent()
    data object OnAddCommentClick : ProfileEvent()
}


data class ProfileState(
    val user: UserDetails? = null,
    val isLoadingUserData: Boolean = false,
    val shouldShowDialog: Boolean = false,
    val selectedFilter: Int = 0,
    val selectedImage: Uri? = null,
    val userPosts: List<Post> = emptyList(),
    val comments: List<Comment>? = emptyList(),
    val comment: String = String(),
    val isLoading: Boolean = false,
    val selectedPostId: String = String(),
    val isRefreshing: Boolean = false
)