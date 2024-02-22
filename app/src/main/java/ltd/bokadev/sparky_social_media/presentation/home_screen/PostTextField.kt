package ltd.bokadev.sparky_social_media.presentation.home_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.utils.Constants.MAX_POST_LENGTH
import ltd.bokadev.sparky_social_media.presentation.MainViewModel
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun PostTextField(
    viewModel: MainViewModel, onValueChange: (String) -> Unit, modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier, contentAlignment = Alignment.BottomEnd
    ) {
        OutlinedTextField(value = viewModel.state.message, onValueChange = {
            onValueChange(it)
        }, colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SparkyTheme.colors.white.copy(0.1f),
            unfocusedContainerColor = SparkyTheme.colors.white.copy(0.1f),
            focusedBorderColor = SparkyTheme.colors.white.copy(0.1f),
            unfocusedBorderColor = SparkyTheme.colors.white.copy(0.1f),
            disabledBorderColor = SparkyTheme.colors.white.copy(0.1f),
            focusedTextColor = SparkyTheme.colors.white,
            disabledTextColor = SparkyTheme.colors.white,
            focusedPlaceholderColor = SparkyTheme.colors.white
        ), modifier = Modifier.fillMaxSize(), placeholder = {
            Text(
                text = stringResource(R.string.enter_your_message_here),
                color = SparkyTheme.colors.white,
                style = SparkyTheme.typography.poppinsRegular14
            )
        })
        Text(
            text = "${viewModel.state.message.length}/$MAX_POST_LENGTH",
            textAlign = TextAlign.End,
            color = SparkyTheme.colors.white,
            style = SparkyTheme.typography.poppinsRegular12,
            modifier = Modifier.padding(bottom = 10.dp, end = 10.dp)
        )
    }


}