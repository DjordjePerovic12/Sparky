package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.PostIdRequest
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
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

            is HomeScreenEvent.OnCommentsClick -> {
                state = state.copy(selectedPostId = event.postId)
                executeGetComments(event.postId)
            }

            is HomeScreenEvent.OnCommentChanged -> {
                if (event.comment.length <= 280) state = state.copy(comment = event.comment)
            }

            is HomeScreenEvent.OnAddCommentClick -> {
                state = state.copy(isLoading = true)
                executeAddComment()
            }

            is HomeScreenEvent.OnLikeClick -> {
                if (event.post.isLiked) executeUnlikePost(
                    postId = event.post.id, likeCount = event.post.likeCount
                ) else executeLikePost(
                    postId = event.post.id, likeCount = event.post.likeCount
                )
            }

        }
    }

    private fun executeGetFeedPosts() {
        viewModelScope.launch {
            val response = repository.getFeedPosts(
                pageCount = 20
            ).cachedIn(viewModelScope)
            response.collectLatest {
                _posts.value = it
            }
        }.invokeOnCompletion { state = state.copy(isRefreshing = false) }
    }

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
                    triggerRefresh()
                }

                is Resource.Error -> {
                    state = state.copy(isLoading = false)
                    _snackBarChannel.send("Error posting comment")
                }

                else -> {}
            }
        }
    }


    //Not sure about the likeCount part, but did it here for now just so I would get your opinion on it
    private fun executeLikePost(postId: String, likeCount: Long) {
        viewModelScope.launch {
            when (repository.likePost(PostIdRequest(postId = postId))) {
                is Resource.Success -> {
                    _posts.update { currentPagingData ->
                        currentPagingData.map { pagingItem ->
                            if (pagingItem.id == postId) {
                                pagingItem.copy(isLiked = true, likeCount = likeCount + 1)
                            } else {
                                pagingItem
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    _snackBarChannel.send("Error liking post")
                }

                else -> {}
            }
        }
    }

    private fun executeUnlikePost(postId: String, likeCount: Long) {
        viewModelScope.launch {
            when (repository.unlikePost(PostIdRequest(postId = postId))) {
                is Resource.Success -> {
                    _posts.update { currentPagingData ->
                        currentPagingData.map { pagingItem ->
                            if (pagingItem.id == postId) {
                                pagingItem.copy(isLiked = false, likeCount = likeCount - 1)
                            } else {
                                pagingItem
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    _snackBarChannel.send("Error unliking post")
                }

                else -> {}
            }
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
    data class OnCommentsClick(val postId: String) : HomeScreenEvent()
    data class OnCommentChanged(val comment: String) : HomeScreenEvent()
    data object OnAddCommentClick : HomeScreenEvent()
    data class OnLikeClick(val post: Post) : HomeScreenEvent()
}

data class HomeScreenState(
    val postContent: String = String(),
    val comments: List<Comment>? = emptyList(),
    val comment: String = String(),
    val isLoading: Boolean = false,
    val selectedPostId: String = String(),
    val isRefreshing: Boolean = false
)