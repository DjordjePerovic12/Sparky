package ltd.bokadev.sparky_social_media.core.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
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
fun UserDataTextField(
    bringIntoViewRequester: BringIntoViewRequester,
    onShowPasswordClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions,
    scope: CoroutineScope,
    placeholderText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIconId: Int,
    value: String,
    trailingIconId: Int? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(value = value,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(16.dp),
        keyboardActions = CustomModifiers.keyboardActions(),
        keyboardOptions = keyboardOptions,
        colors = CustomModifiers.textFieldColors(),
        textStyle = SparkyTheme.typography.poppinsRegular14,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(text = placeholderText)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIconId), contentDescription = null)
        },
        trailingIcon = {
            if (trailingIconId != null) Icon(
                painter = painterResource(id = trailingIconId), contentDescription = null,
                modifier = Modifier.noRippleClickable {
                    if (trailingIconId == R.drawable.ic_show_password && onShowPasswordClick != null) onShowPasswordClick()
                }
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            })
}