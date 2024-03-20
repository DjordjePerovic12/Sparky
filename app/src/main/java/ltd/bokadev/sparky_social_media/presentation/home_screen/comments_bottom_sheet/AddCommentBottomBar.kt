package ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.getInitials
import ltd.bokadev.sparky_social_media.presentation.home_screen.UserImageItem
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddCommentBottomBar(
    username: String,
    isLoading: Boolean,
    imageUrl: String? = null,
    comment: String,
    onCommentChange: (String) -> Unit,
    onAddCommentClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(SparkyTheme.colors.primaryColor)
            .padding(horizontal = 12.dp, vertical = 20.dp)
    ) {
        //Will refactor this when I refactor the image item in general
        //Because the one I made earlier isn't appropriate for this component
        //And my focus at the moment is on functional parts of the app
        UserImageItem(frameSize = 50.dp, userFullName = username, imageUrl = imageUrl)

        AddCommentTextField(bringIntoViewRequester = bringIntoViewRequester,
            keyboardOptions = CustomModifiers.textKeyboardNext(),
            scope = scope,
            placeholderText = stringResource(R.string.add_comment),
            value = comment,
            onValueChange = { onCommentChange(it) })
        FloatingActionButton(
            onClick = {
                keyboardController?.hide()
                onAddCommentClick()
            },
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            containerColor = SparkyTheme.colors.yellow
        ) {
            if (isLoading) CircularProgressIndicator(color = SparkyTheme.colors.primaryColor) else Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = SparkyTheme.colors.primaryColor
            )
        }
    }
}