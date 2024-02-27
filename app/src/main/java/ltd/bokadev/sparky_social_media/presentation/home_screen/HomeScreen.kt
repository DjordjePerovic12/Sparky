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
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.utils.hideKeyboard
import ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet.CommentsBottomSheet
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel
) {
    val state = viewModel.state
    val posts = viewModel.posts.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )

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
                    viewModel.onEvent(HomeScreenEvent.OnAddCommentClicked)
                },
                onCommentChange = {
                    viewModel.onEvent(HomeScreenEvent.OnCommentChanged(it))
                })
        }) {
        Scaffold(
            topBar = {
                SparkyTopBar(style = "Home", onSearchClick = {
                    viewModel.onEvent(HomeScreenEvent.OnSearchClick)
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
                if (state.isRefreshing) {
                    item {
                        CircularProgressIndicator(color = SparkyTheme.colors.primaryColor)
                    }
                } else items(posts.itemCount) { index ->
                    val post = posts[index]
                    if (post != null) {
                        //Will update the post item to match design after all functionalities are implemented
                        SparkyPostItem(post = post, onCommentsClick = {
                            scope.launch {
                                bottomSheetState.show()
                            }
                            viewModel.onEvent(HomeScreenEvent.OnCommentsClicked(post.id))
                        })
                    }
                }
            }
        }
    }
}