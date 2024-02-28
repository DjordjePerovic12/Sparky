package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.core.components.LogoutAlertDialog
import ltd.bokadev.sparky_social_media.core.utils.Mocks.mockUserDetails
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val state = viewModel.state

    LogoutAlertDialog(headerText = "LOG OUT",
        messageText = "Are you sure you want to logout?",
        shouldShow = state.shouldShowDialog,
        onDismissRequest = { viewModel.onEvent(ProfileEvent.OnCloseClick) }) {
        viewModel.onEvent(ProfileEvent.OnConfirmClick)
    }

    Scaffold(topBar = {
        state.user?.let {
            ProfileScreenTopBar(
                user = it, isLoadingUserData = state.isLoadingUserData
            ) {
                viewModel.onEvent(ProfileEvent.OnLogoutClick)
            }
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp)
                .background(SparkyTheme.colors.white)
        ) {
            //Using static data everywhere because this PR was all about UI
            //Also wanted to know if this PostFilters enum approach is ok
            stickyHeader {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(PostFilters.entries.size) {
                        UserPostsTabItem(
                            isSelected = state.selectedFilter == PostFilters.entries[it].id,
                            filter = PostFilters.entries[it]
                        ) { filter ->
                            viewModel.onEvent(ProfileEvent.OnPostFilterClick(filter.id))
                        }
                    }
                }
            }
        }
    }

}