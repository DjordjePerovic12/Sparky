package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    showSnackBar: (String) -> Unit
) {
    val state = viewModel.state
    val users = viewModel.users.collectAsLazyPagingItems()
    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    Scaffold(topBar = {
        SparkyTopBar(style = "Search",
            searchQuery = state.searchQuery,
            onSearchChange = { viewModel.onEvent(SearchEvent.OnSearchQueryChange(it)) })
    }) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .padding(innerPadding)
        ) {
            items(users.itemCount) { index ->
                val user = users[index]
                if (user != null) {
                    Timber.e("USER IS $user")
                    UserItem(user = user)
                }
            }
            users.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator(color = SparkyTheme.colors.yellow) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        viewModel.onEvent(SearchEvent.OnFetchingUsersError)
                    }
                }
            }

        }

    }
}