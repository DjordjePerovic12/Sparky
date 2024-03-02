package ltd.bokadev.sparky_social_media.presentation.profile_screen

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.core.components.LogoutAlertDialog
import ltd.bokadev.sparky_social_media.core.utils.Mocks.mockUserDetails
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.utils.getImage
import ltd.bokadev.sparky_social_media.presentation.home_screen.SparkyPostItem
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    showSnackBar: (message: String) -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current



    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val image = getImage(it, context = context)
            viewModel.onEvent(ProfileEvent.ImageSelected(image))
        }
    }

    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    LogoutAlertDialog(headerText = "LOG OUT",
        messageText = "Are you sure you want to logout?",
        shouldShow = state.shouldShowDialog,
        onDismissRequest = { viewModel.onEvent(ProfileEvent.OnCloseClick) }) {
        viewModel.onEvent(ProfileEvent.OnConfirmClick)
    }

    //IMO UX was lame without some kind of confirmation of the photo you selected
    //so I added this
    LaunchedEffect(key1 = state.user) {
        Timber.e("Image url ${state.user?.profilePictureUrl}")
        Timber.e("POSTS ${state.userPosts}")
    }

    Scaffold(topBar = {
        state.user?.let {
            ProfileScreenTopBar(user = it,
                isLoadingUserData = state.isLoadingUserData,
                onChangeProfilePictureClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
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
            if (state.selectedFilter == PostFilters.YOUR_POSTS.id)
                items(state.userPosts) { post ->
                    SparkyPostItem(post = post, onLikeClick = {}) {

                    }
                }
        }
    }

}