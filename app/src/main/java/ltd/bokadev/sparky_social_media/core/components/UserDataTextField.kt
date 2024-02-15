package ltd.bokadev.sparky_social_media.core.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserDataTextField(
    bringIntoViewRequester: BringIntoViewRequester,
    scope: CoroutineScope,
    placeholderText: String,
    leadingIconId: Int,
    trailingIconId: Int? = null
) {
    OutlinedTextField(value = "",
        onValueChange = {},
        shape = RoundedCornerShape(16.dp),
        keyboardActions = CustomModifiers.keyboardActions(),
        keyboardOptions = CustomModifiers.emailKeyboard(),
        colors = CustomModifiers.textFieldColors(),
        textStyle = SparkyTheme.typography.poppinsRegular14,
        placeholder = {
            Text(text = placeholderText)
        },
        leadingIcon = {
            Icon(painter = painterResource(id = leadingIconId), contentDescription = null)
        },
        trailingIcon = {
            if (trailingIconId != null) Icon(
                painter = painterResource(id = trailingIconId), contentDescription = null
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