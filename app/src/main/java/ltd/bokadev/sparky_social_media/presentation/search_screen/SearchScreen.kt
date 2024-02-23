package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.collectAsLazyPagingItems
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar

@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    val state = viewModel.state
    val users = viewModel.users.collectAsLazyPagingItems()
    Scaffold(topBar = {
        SparkyTopBar(style = "Search",
            searchQuery = state.searchQuery,
            onSearchChange = { viewModel.onEvent(SearchEvent.OnSearchQueryChange(it)) })
    }) { innerPadding ->
        LazyColumn {
            items(users.itemCount) { index ->
                val user = users[index]
                Text(text = user?.username ?: "")
            }
        }

    }
}