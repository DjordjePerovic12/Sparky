package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.domain.model.RegistrationTime
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import timber.log.Timber
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val navigator: Navigator, private val repository: SparkyRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private var searchJob: Job? = null

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    private val _users = MutableStateFlow<PagingData<UserDetails>>(PagingData.empty())
    val users: StateFlow<PagingData<UserDetails>> = _users.asStateFlow()


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
                if (state.searchQuery.isNotEmpty()) state = state.copy(isLoading = true) else
                    state = state.copy(isLoading = false)
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
                    )
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
}

sealed class SearchEvent {
    data class OnSearchQueryChange(val searchQuery: String) : SearchEvent()
    data object OnFetchingUsersError : SearchEvent()
    data object OnCrossClick : SearchEvent()
    data object TriggerLoader : SearchEvent()
}

data class SearchState(
    val searchQuery: String = String(),
    val users: List<UserDetails> = emptyList(),
    val isLoading: Boolean = false
)