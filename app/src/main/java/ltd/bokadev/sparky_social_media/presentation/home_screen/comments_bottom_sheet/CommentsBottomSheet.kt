package ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun CommentsBottomSheet(
    comments: List<Comment>,
    username: String,
    isLoading: Boolean,
    isRefreshing: Boolean,
    imageUrl: String? = null,
    comment: String,
    onAddCommentClick: () -> Unit,
    onCommentChange: (String) -> Unit
) {
    Scaffold(bottomBar = {
        AddCommentBottomBar(
            username = username,
            imageUrl = imageUrl,
            comment = comment,
            isLoading = isLoading,
            onAddCommentClick = { onAddCommentClick() },
            onCommentChange = {
                onCommentChange(it)
            })
    }, topBar = {
        AddCommentTopBar()
    },
        modifier = Modifier.clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(SparkyTheme.colors.primaryColor)
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            if (isRefreshing) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = SparkyTheme.colors.yellow)
                    }
                }
            } else if (comments.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.there_are_no_comments_yet_write_the_first_one),
                        color = SparkyTheme.colors.white,
                        style = SparkyTheme.typography.poppinsRegular16
                    )
                }
            } else {
                items(comments) { comment ->
                    CommentItem(comment = comment)
                }
            }
        }
    }
}