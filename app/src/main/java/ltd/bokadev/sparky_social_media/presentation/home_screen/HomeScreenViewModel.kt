package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.login_screen.LoginState
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: SparkyRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    //TODO: Implement home screen viewModel

    var state by mutableStateOf(HomeScreenState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    private val _posts = MutableStateFlow<PagingData<Post>>(PagingData.empty())
    val posts: StateFlow<PagingData<Post>> = _posts.asStateFlow()

    init {
        executeGetFeedPosts()
        getUserData()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnCreatePostClick -> {

            }

            is HomeScreenEvent.OnSearchClick -> {
                navigateToSearchScreen()
            }

            is HomeScreenEvent.OnCommentsClicked -> {
                state = state.copy(selectedPostId = event.postId)
                executeGetComments(event.postId)
            }

            is HomeScreenEvent.OnCommentChanged -> {
                if (event.comment.length <= 280) state = state.copy(comment = event.comment)
            }

            is HomeScreenEvent.OnAddCommentClicked -> {
                state = state.copy(isLoading = true)
                executeAddComment()
            }

        }
    }

    private fun executeGetFeedPosts() {
        viewModelScope.launch {
            val response = repository.getFeedPosts(
                pageCount = 20
            )
            response.collectLatest {
                _posts.value = it
            }
        }.invokeOnCompletion { state = state.copy(isRefreshing = false) }
    }

    private fun executeGetComments(postId: String) {
        viewModelScope.launch {
            repository.getPostComments(postId)
                .collectLatestWithAuthCheck(navigator = navigator, onSuccess = { result ->
                    result.data.let { comments ->
                        state = state.copy(comments = comments, isRefreshing = false)
                    }
                }, onError = {
                    _snackBarChannel.send("Error fetching comments")
                })
        }
    }

    private fun executeAddComment() {
        viewModelScope.launch {
            repository.addComment(
                CommentRequest(
                    postId = state.selectedPostId, content = state.comment
                )
            ).collectLatestWithAuthCheck(navigator = navigator, onSuccess = {
                state = state.copy(isLoading = false, comment = String())
                triggerRefresh()
            }, onError = {
                state = state.copy(isLoading = false)
                _snackBarChannel.send("Error posting comment")
            }

            )
        }
    }

    //Function that fetches data to get updated comments in the bottomSheet, and commentCount on post item
    //Also triggers a loader so it look smooth
    private fun triggerRefresh() {
        state = state.copy(isRefreshing = true)
        executeGetComments(state.selectedPostId)
        executeGetFeedPosts()
    }

    private fun navigateToSearchScreen() {
        viewModelScope.launch {
            navigator.navigateTo(Screen.SearchScreen.route)
        }
    }

    fun getUserData() = runBlocking {
        withContext(Dispatchers.IO) {
            dataStoreRepository.getUser().first()
        }
    }
}

sealed class HomeScreenEvent {
    data class OnCreatePostClick(val content: String) : HomeScreenEvent()
    data object OnSearchClick : HomeScreenEvent()
    data class OnCommentsClicked(val postId: String) : HomeScreenEvent()
    data class OnCommentChanged(val comment: String) : HomeScreenEvent()
    data object OnAddCommentClicked : HomeScreenEvent()
}

data class HomeScreenState(
    val postContent: String = String(),
    val comments: List<Comment>? = emptyList(),
    val comment: String = String(),
    val isLoading: Boolean = false,
    val selectedPostId: String = String(),
    val isRefreshing: Boolean = false
)