package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.core.utils.collectLatestWithAuthCheck
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserIdRequest
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val navigator: Navigator, private val repository: SparkyRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null
    private var followJob: Job? = null
    private var unfollowJob: Job? = null

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    private val _users = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val users: StateFlow<PagingData<User>> = _users.asStateFlow()


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchQueryChange -> {
                Timber.e("TRIGGERED")
                state = state.copy(searchQuery = event.searchQuery)
                searchDebounce(event.searchQuery)
            }

            is SearchEvent.OnFetchingUsersError -> {
                viewModelScope.launch {
                    _snackBarChannel.send("Error fetching users")
                }
            }

            is SearchEvent.OnCrossClick -> {
                state = state.copy(searchQuery = String())
            }

            is SearchEvent.TriggerLoader -> {
                state =
                    if (state.searchQuery.isNotEmpty()) state.copy(isLoading = true) else state.copy(
                        isLoading = false
                    )
            }

            is SearchEvent.OnFollowUnfollowClick -> {
                viewModelScope.launch {
                    if (event.user.isFollowing) executeUnfollowUser(event.user)
                    else executeFollowUser(event.user)
                }
            }
        }
    }


    private fun searchDebounce(searchQuery: String) {
        state = state.copy(isLoading = true)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (searchQuery.isNotEmpty()) {
                try {
                    val result = repository.searchProfiles(
                        searchQuery = searchQuery, pageCount = 20
                    ).cachedIn(viewModelScope)
                    result.collectLatest {
                        _users.value = it
                        state = state.copy(isLoading = false)
                    }
                } catch (e: Exception) {
                    _snackBarChannel.send("Error fetching users.")
                } finally {
                    state =
                        state.copy(isLoading = false)  // Set loading state to false when search completes
                }
            }
        }
    }

    private fun executeFollowUser(user: User) {
        followJob?.cancel()
        followJob = viewModelScope.launch {
            delay(500)
            when (repository.followUser(UserIdRequest(user.user.id))) {
                is Resource.Success -> {
                    _users.update { currentPagingData ->
                        currentPagingData.map { pagingItem ->
                            if (pagingItem.user.id == user.user.id) {
                                pagingItem.copy(isFollowing = true)
                            } else {
                                pagingItem
                            }
                        }
                    }

                    _snackBarChannel.send("Successfully followed ${user.user.username} ")
                }

                is Resource.Error -> {
                    _snackBarChannel.send("Error following user")
                }


                else -> {}
            }
        }
    }

    private fun executeUnfollowUser(user: User) {
        unfollowJob?.cancel()
        unfollowJob = viewModelScope.launch {
            delay(500)
            when (repository.unfollowUser(UserIdRequest(user.user.id))) {
                is Resource.Success -> {
                    _users.update { currentPagingData ->
                        currentPagingData.map { pagingItem ->
                            if (pagingItem.user.id == user.user.id) {
                                pagingItem.copy(isFollowing = false)
                            } else {
                                pagingItem
                            }
                        }
                    }

                    _snackBarChannel.send("Successfully unfollowed ${user.user.username} ")
                }

                is Resource.Error -> {
                    _snackBarChannel.send("Error unfollowing user")
                }


                else -> {}
            }
        }
    }
}

sealed class SearchEvent {
    data class OnSearchQueryChange(val searchQuery: String) : SearchEvent()
    data object OnFetchingUsersError : SearchEvent()
    data object OnCrossClick : SearchEvent()
    data object TriggerLoader : SearchEvent()
    data class OnFollowUnfollowClick(val user: User) : SearchEvent()
}

data class SearchState(
    val searchQuery: String = String(),
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false
)