package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.utils.hideKeyboard

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    Scaffold(
        topBar = {
            SparkyTopBar(
                style = "Home",
                onSearchClick = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchClick)
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(top = 15.dp, bottom = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .hideKeyboard(LocalFocusManager.current)
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            items(5) {
                SparkyPostItem(
                    userFullName = "Petar Gajevic",
                    createdAt = "3:45 - April 1, 2024",
                    postContent = "This is where your description lives. It’s limited to 160 characters including.",
                    likes = 434,
                    comments = 2342
                )
            }
        }
    }
}