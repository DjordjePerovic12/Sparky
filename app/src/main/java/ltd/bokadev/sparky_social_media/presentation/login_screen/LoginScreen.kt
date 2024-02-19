package ltd.bokadev.sparky_social_media.presentation.login_screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.components.PrimaryButton
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.components.UserDataTextField
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    showSnackBar: (message: String) -> Unit
) {

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    val state = viewModel.state

    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    Scaffold(topBar = { SparkyTopBar() }, bottomBar = {
        LoginBottomBar {
            viewModel.onEvent(LoginEvent.OnRegisterClick)
        }
    }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.hi_there),
                color = SparkyTheme.colors.primaryColor,
                style = SparkyTheme.typography.poppinsSemiBold24
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.experience_the_world_at_your_fingertips_with_our_social_mobile_app),
                color = SparkyTheme.colors.textLightGray,
                style = SparkyTheme.typography.poppinsRegular14
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(R.string.email),
                style = SparkyTheme.typography.poppinsRegular14,
                color = SparkyTheme.colors.textLightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            UserDataTextField(
                bringIntoViewRequester = bringIntoViewRequester,
                scope = scope,
                placeholderText = "Email",
                leadingIconId = R.drawable.ic_email,
                onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                value = state.email,
                keyboardOptions = CustomModifiers.emailKeyboard(),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.password),
                style = SparkyTheme.typography.poppinsRegular14,
                color = SparkyTheme.colors.textLightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            UserDataTextField(
                bringIntoViewRequester = bringIntoViewRequester,
                scope = scope,
                placeholderText = "Password",
                leadingIconId = R.drawable.ic_lock,
                trailingIconId = R.drawable.ic_show_password,
                onShowPasswordClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                value = state.password,
                keyboardOptions = CustomModifiers.passwordKeyboardDone(),
            )
            Spacer(modifier = Modifier.height(50.dp))
            PrimaryButton(
                text = stringResource(R.string.login),
                //TODO: Handle colors when button is disabled
                enabled = state.shouldEnableLogin,
                color = if (state.shouldEnableLogin) SparkyTheme.colors.yellow else SparkyTheme.colors.primaryColor,
                borderColor = if (state.shouldEnableLogin) SparkyTheme.colors.yellow else SparkyTheme.colors.primaryColor,
                textColor = if (state.shouldEnableLogin) SparkyTheme.colors.primaryColor else SparkyTheme.colors.white,
                textStyle = SparkyTheme.typography.poppinsMedium16,
                modifier = Modifier.height(50.dp)
            ) {
                viewModel.onEvent(LoginEvent.OnLoginClick)
            }
        }
    }
}