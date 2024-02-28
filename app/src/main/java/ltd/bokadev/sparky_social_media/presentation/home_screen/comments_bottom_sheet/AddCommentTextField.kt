package ltd.bokadev.sparky_social_media.presentation.home_screen.comments_bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.noRippleClickable
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddCommentTextField(
    bringIntoViewRequester: BringIntoViewRequester,
    keyboardOptions: KeyboardOptions,
    scope: CoroutineScope,
    placeholderText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(value = value,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(16.dp),
        keyboardActions = CustomModifiers.keyboardActions(),
        keyboardOptions = keyboardOptions,
        colors = CustomModifiers.addCommentTextFieldColor(),
        textStyle = SparkyTheme.typography.poppinsRegular16,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(text = placeholderText)
        },
        modifier = Modifier
            .width(240.dp)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
    )
}