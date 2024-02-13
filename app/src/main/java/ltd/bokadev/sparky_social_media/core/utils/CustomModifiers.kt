package ltd.bokadev.sparky_social_media.core.utils

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

object CustomModifiers {
//    val textFieldColors: @Composable () -> TextFieldColors = {
//        TextFieldDefaults.outlinedTextFieldColors(
//            focusedBorderColor = LusticaTheme.colors.darkBlue,
//            unfocusedBorderColor = LusticaTheme.colors.grayBorder,
//            cursorColor = LusticaTheme.colors.darkBlue,
//            textColor = LusticaTheme.colors.darkBlue
//        )
//    }

    val snackBarHost: @Composable () -> Unit = {
        SnackbarHost(hostState = SnackbarHostState()) { snackBarData ->
            Snackbar(
                snackbarData = snackBarData,
                containerColor = SparkyTheme.colors.black,
                contentColor = SparkyTheme.colors.white,
                actionColor = SparkyTheme.colors.white
            )
        }
    }
    val textKeyboardNext: @Composable () -> KeyboardOptions = {
        KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        )
    }

    val passwordKeyboardDone: @Composable () -> KeyboardOptions = {
        KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
        )
    }

    val keyboardActions: @Composable () -> KeyboardActions = {
        val focusManager = LocalFocusManager.current
        KeyboardActions(onDone = { focusManager.clearFocus() },
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onPrevious = { focusManager.moveFocus(FocusDirection.Up) })
    }
}
