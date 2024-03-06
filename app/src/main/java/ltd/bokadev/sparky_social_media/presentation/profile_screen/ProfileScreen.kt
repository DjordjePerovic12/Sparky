package ltd.bokadev.sparky_social_media.presentation.profile_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.components.LogoutAlertDialog
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.data.utils.getImage
import ltd.bokadev.sparky_social_media.presentation.home_screen.HomeScreenEvent
import ltd.bokadev.sparky_social_media.presentation.home_screen.SparkyPostItem
import ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet.CommentsBottomSheet
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel, showSnackBar: (message: String) -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing, onRefresh = { viewModel.refresh() }, refreshThreshold = 60.dp
    )


    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val image = getImage(it, context = context)
            viewModel.onEvent(ProfileEvent.ImageSelected(image))
        }
    }

    val userPosts = viewModel.executeGetProfilePosts().collectAsLazyPagingItems()
    val likedPosts = viewModel.executeGetLikedPosts().collectAsLazyPagingItems()


    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    LogoutAlertDialog(headerText = "LOG OUT",
        messageText = "Are you sure you want to logout?",
        shouldShow = state.shouldShowDialog,
        onDismissRequest = { viewModel.onEvent(ProfileEvent.OnCloseClick) }) {
        viewModel.onEvent(ProfileEvent.OnConfirmClick)
    }

    ModalBottomSheetLayout(sheetState = bottomSheetState,
        modifier = Modifier.background(SparkyTheme.colors.primaryColor),
        sheetContent = {
            CommentsBottomSheet(comments = state.comments ?: emptyList(),
                username = viewModel.getUserData().username,
                imageUrl = null,
                isLoading = state.isLoading,
                isRefreshing = state.isRefreshing,
                comment = state.comment,
                onAddCommentClick = {
                    viewModel.onEvent(ProfileEvent.OnAddCommentClick)
                },
                onCommentChange = {
                    viewModel.onEvent(ProfileEvent.OnCommentChanged(it))
                })
        }) {
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
                    .pullRefresh(pullRefreshState),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                stickyHeader {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SparkyTheme.colors.white),
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
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        PullRefreshIndicator(
                            refreshing = isRefreshing,
                            state = pullRefreshState,
                            backgroundColor = SparkyTheme.colors.white,
                            contentColor = SparkyTheme.colors.yellow
                        )
                    }
                }
                if (state.selectedFilter == PostFilters.YOUR_POSTS.id) items(userPosts.itemCount) { index ->
                    val post = userPosts[index]
                    if (post != null) {
                        SparkyPostItem(post = post, onLikeClick = {}) {
                            scope.launch {
                                bottomSheetState.show()
                            }
                            viewModel.onEvent(ProfileEvent.OnCommentsClick(post.id))
                        }
                    }
                }
                else items(likedPosts.itemCount) { index ->
                    val post = likedPosts[index]
                    if (post != null) {
                        SparkyPostItem(post = post, onLikeClick = {}) {
                            scope.launch {
                                bottomSheetState.show()
                            }
                            viewModel.onEvent(ProfileEvent.OnCommentsClick(post.id))
                        }
                    }
                }
            }
        }
    }
}