package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.hideKeyboard
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet.CommentsBottomSheet
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentEvent
import ltd.bokadev.sparky_social_media.presentation.shared_view_models.CommentsViewModel
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeScreenViewModel,
    commentsViewModel: CommentsViewModel,
    navController: NavController,
    showSnackBar: (message: String) -> Unit,
) {
    val homeScreenState = homeViewModel.state
    val commentState = commentsViewModel.state
    val posts = homeViewModel.posts.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

    homeViewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    commentsViewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }


    ModalBottomSheetLayout(sheetState = bottomSheetState,
        modifier = Modifier.background(SparkyTheme.colors.primaryColor),
        sheetContent = {
            CommentsBottomSheet(comments = commentState.comments ?: emptyList(),
                username = homeViewModel.getUserData().username,
                imageUrl = null,
                isLoading = commentState.isLoading,
                isRefreshing = commentState.isRefreshing,
                comment = commentState.comment,
                onAddCommentClick = {
                    commentsViewModel.onEvent(CommentEvent.OnAddCommentClick)
                },
                onUserImageClick = {
                    navController.navigate(Screen.ProfileScreen.passUserId(it.id))
                },
                onCommentChange = {
                    commentsViewModel.onEvent(CommentEvent.OnCommentChanged(it))
                })
        }) {
        Scaffold(
            topBar = {
                SparkyTopBar(style = "Home", onSearchClick = {
                    homeViewModel.onEvent(HomeScreenEvent.OnSearchClick)
                },
                    onNotificationsClick = {
                        homeViewModel.onEvent(HomeScreenEvent.OnNotificationsClick)
                    })
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
                if (commentState.isRefreshing) {
                    item {
                        CircularProgressIndicator(color = SparkyTheme.colors.primaryColor)
                    }
                } else items(posts.itemCount) { index ->
                    val post = posts[index]
                    if (post != null) {
                        //Will update the post item to match design after all functionalities are implemented
                        SparkyPostItem(post = post, onLikeClick = {
                            homeViewModel.onEvent(HomeScreenEvent.OnLikeClick(it))
                        }, onCommentsClick = {
                            scope.launch {
                                bottomSheetState.show()
                            }
                            commentsViewModel.onEvent(CommentEvent.OnCommentsClick(post.id))
                        },
                            onUserImageClick = {
                                navController.navigate(Screen.ProfileScreen.passUserId(it.id))
                            })
                    }
                }
            }
        }
    }
}