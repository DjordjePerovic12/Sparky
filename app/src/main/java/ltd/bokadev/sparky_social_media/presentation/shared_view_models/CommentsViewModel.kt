package ltd.bokadev.sparky_social_media.presentation.shared_view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreenEvent
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: SparkyRepository
) : ViewModel() {

    var state by mutableStateOf(CommentState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()


    fun onEvent(event: CommentEvent) {
        when (event) {
            is CommentEvent.OnCommentsClick -> {
                state = state.copy(selectedPostId = event.postId)
                executeGetComments(event.postId)
            }

            is CommentEvent.OnCommentChanged -> {
                if (event.comment.length <= 280) state = state.copy(comment = event.comment)
            }

            is CommentEvent.OnAddCommentClick -> {
                state = state.copy(isLoading = true)
                executeAddComment()
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            executeGetComments(state.selectedPostId)
            _isRefreshing.emit(false)
        }
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
}

sealed class CommentEvent {
    data class OnCommentsClick(val postId: String) : CommentEvent()
    data class OnCommentChanged(val comment: String) : CommentEvent()
    data object OnAddCommentClick : CommentEvent()
}

data class CommentState(
    val comments: List<Comment>? = emptyList(),
    val comment: String = String(),
    val selectedPostId: String = String(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)