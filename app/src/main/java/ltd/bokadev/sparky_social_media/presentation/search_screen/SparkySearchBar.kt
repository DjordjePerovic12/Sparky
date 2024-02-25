package ltd.bokadev.sparky_social_media.presentation.search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SparkySearchBar(
    searchQuery: String,
    keyboardOptions: KeyboardOptions,
    onCrossClick: () -> Unit,
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
                modifier = Modifier
                    .size(12.dp)
                    .clickable {
                        onCrossClick()
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