package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    viewModel: SearchViewModel, showSnackBar: (String) -> Unit
) {
    val state = viewModel.state
    val users = viewModel.users.collectAsLazyPagingItems()

    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }
    //On every query parameter change I want to show the circular indicator while the API responds
    LaunchedEffect(key1 = state.searchQuery) {
        viewModel.onEvent(SearchEvent.TriggerLoader)
    }
    Scaffold(topBar = {
        SparkyTopBar(style = "Search",
            searchQuery = state.searchQuery,
            onCrossClick = { viewModel.onEvent(SearchEvent.OnCrossClick) },
            onSearchChange = {
                viewModel.onEvent(SearchEvent.OnSearchQueryChange(it))
            })
    }) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 15.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
        ) {
            if (!state.isLoading) {
                if (users.itemCount != 0) {
                    items(users.itemCount) { index ->
                        val user = users[index]
                        if (user != null) {
                            UserItem(
                                user = user,
                                onFollowClick = {
                                    viewModel.onEvent(SearchEvent.OnFollowClick(it))
                                }
                            )
                        }
                    }
                } else item {
                    if (state.searchQuery.isNotEmpty()) Text(
                        text = "No username matches your query parameters.",
                        color = SparkyTheme.colors.primaryColor,
                        style = SparkyTheme.typography.poppinsRegular14
                    )
                }
            } else item { CircularProgressIndicator(color = SparkyTheme.colors.yellow) }
        }

    }
}