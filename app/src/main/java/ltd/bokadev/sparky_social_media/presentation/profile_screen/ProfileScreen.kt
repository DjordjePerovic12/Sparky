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
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.components.LogoutAlertDialog
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.ProfileScreenType
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.data.utils.getImage
import ltd.bokadev.sparky_social_media.presentation.home_screen.SparkyPostItem
import ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet.CommentsBottomSheet
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentEvent
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentsViewModel
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    commentsViewModel: CommentsViewModel,
    navController: NavController,
    showSnackBar: (message: String) -> Unit
) {
    val profileState = profileViewModel.state
    val commentState = commentsViewModel.state
    val context = LocalContext.current
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


    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            val image = getImage(it, context = context)
            profileViewModel.onEvent(ProfileEvent.ImageSelected(image))
        }
    }
    val posts = when (profileState.selectedFilter) {
        PostFilters.YOUR_POSTS -> {
            profileViewModel.executeGetProfilePosts().collectAsLazyPagingItems()
        }

        PostFilters.LIKED_POSTS -> {
            profileViewModel.executeGetLikedPosts().collectAsLazyPagingItems()
        }
    }
    profileViewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    commentsViewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    LogoutAlertDialog(headerText = "LOG OUT",
        messageText = "Are you sure you want to logout?",
        shouldShow = profileState.shouldShowDialog,
        onDismissRequest = { profileViewModel.onEvent(ProfileEvent.OnCloseClick) }) {
        profileViewModel.onEvent(ProfileEvent.OnConfirmClick)
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
                onUserImageClick = { user ->
                    Screen.ProfileScreen.passUserId(user.id)
                        ?.let { navController.navigate(it) }
                },
                onCommentChange = {
                    commentsViewModel.onEvent(CommentEvent.OnCommentChanged(it))
                })
        }) {
        Scaffold(topBar = {
            profileState.user?.let {
                ProfileScreenTopBar(user = it,
                    type = profileState.profileScreenType,
                    isLoadingUserData = profileState.isLoadingUserData,
                    onChangeProfilePictureClick = {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }) {
                    profileViewModel.onEvent(ProfileEvent.OnLogoutClick)
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
                                isSelected = profileState.selectedFilter == PostFilters.entries[it],
                                filter = PostFilters.entries[it],
                                profileScreenType = profileState.profileScreenType
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
                items(posts.itemCount) { index ->
                    val post = posts[index]
                    if (post != null) {
                        SparkyPostItem(
                            post = post,
                            onLikeClick = {},
                            onCommentsClick = {
                                scope.launch {
                                    bottomSheetState.show()
                                }
                                commentsViewModel.onEvent(
                                    CommentEvent.OnCommentsClick(
                                        post.id
                                    )
                                )
                            },
                            onUserImageClick = {
                                navController.navigate(Screen.ProfileScreen.passUserId(it.id))
                            }
                        )
                    }
                }
            }
        }
    }
}