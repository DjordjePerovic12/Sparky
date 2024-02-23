package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.noRippleClickable
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CustomSearchBar(
    searchQuery: String,
    keyboardOptions: KeyboardOptions,
    bringIntoViewRequester: BringIntoViewRequester,
    visualTransformation: VisualTransformation,
    placeholder: String,
    focusRequester: FocusRequester,
    onSearchChanged: (searchQuery: String) -> Unit
) {
    val scope = rememberCoroutineScope()

    OutlinedTextField(value = searchQuery,
        onValueChange = { onSearchChanged(it) },
        shape = RoundedCornerShape(10.dp),
        keyboardActions = CustomModifiers.keyboardActions(),
        keyboardOptions = keyboardOptions,
        colors = CustomModifiers.searchFieldColors(),
        textStyle = SparkyTheme.typography.poppinsRegular14,
        visualTransformation = visualTransformation,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = SparkyTheme.colors.white,
                style = SparkyTheme.typography.poppinsRegular14
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = SparkyTheme.colors.white
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_cross),
                contentDescription = null,
                tint = SparkyTheme.colors.white,
                modifier = Modifier.size(12.dp)
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