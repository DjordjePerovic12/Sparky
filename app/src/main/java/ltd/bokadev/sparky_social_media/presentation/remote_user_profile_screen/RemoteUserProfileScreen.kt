package ltd.bokadev.sparky_social_media.presentation.remote_user_profile_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.components.LogoutAlertDialog
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.ProfileScreenType
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.data.utils.getImage
import ltd.bokadev.sparky_social_media.presentation.home_screen.SparkyPostItem
import ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet.CommentsBottomSheet
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileEvent
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileScreenTopBar
import ltd.bokadev.sparky_social_media.presentation.profile_screen.ProfileViewModel
import ltd.bokadev.sparky_social_media.presentation.profile_screen.UserPostsTabItem
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentEvent
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentsViewModel
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun RemoteUserProfileScreen(
    profileViewModel: ProfileViewModel,
    commentsViewModel: CommentsViewModel,
    showSnackBar: (message: String) -> Unit
) {
    val profileState = profileViewModel.state
    val commentState = commentsViewModel.state
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    val isRefreshing by commentsViewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { commentsViewModel.refresh() },
        refreshThreshold = 60.dp
    )


    val userPosts = profileViewModel.executeGetProfilePosts().collectAsLazyPagingItems()
    val likedPosts = profileViewModel.executeGetLikedPosts().collectAsLazyPagingItems()


    profileViewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    commentsViewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    ModalBottomSheetLayout(sheetState = bottomSheetState,
        modifier = Modifier.background(SparkyTheme.colors.primaryColor),
        sheetContent = {
            CommentsBottomSheet(comments = commentState.comments ?: emptyList(),
                username = profileViewModel.getUserData().username,
                imageUrl = null,
                isLoading = commentState.isLoading,
                isRefreshing = commentState.isRefreshing,
                comment = commentState.comment,
                onAddCommentClick = {
                    commentsViewModel.onEvent(CommentEvent.OnAddCommentClick)
                },
                onCommentChange = {
                    commentsViewModel.onEvent(CommentEvent.OnCommentChanged(it))
                })
        }) {
        Scaffold(topBar = {
            profileState.user?.let {
                ProfileScreenTopBar(user = it,
                    isLoadingUserData = profileState.isLoadingUserData,
                    type = ProfileScreenType.REMOTE_USER,
                    onChangeProfilePictureClick = {},
                    onLogoutClick = {}
                )
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
                                isSelected = profileState.selectedFilter == PostFilters.entries[it],
                                filter = PostFilters.entries[it]
                            ) { filter ->
                                profileViewModel.onEvent(ProfileEvent.OnPostFilterClick(filter))
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
                if (profileState.selectedFilter == PostFilters.YOUR_POSTS) items(userPosts.itemCount) { index ->
                    val post = userPosts[index]
                    if (post != null) {
                        SparkyPostItem(post = post, onLikeClick = {}, onCommentsClick = {
                            scope.launch {
                                bottomSheetState.show()
                            }
                            commentsViewModel.onEvent(CommentEvent.OnCommentsClick(post.id))
                        })
                    }
                }
                else items(likedPosts.itemCount) { index ->
                    val post = likedPosts[index]
                    if (post != null) {
                        SparkyPostItem(post = post, onLikeClick = {}) {
                            scope.launch {
                                bottomSheetState.show()
                            }
                            commentsViewModel.onEvent(CommentEvent.OnCommentsClick(post.id))
                        }
                    }
                }
            }
        }
    }
}