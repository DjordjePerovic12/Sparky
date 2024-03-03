package ltd.bokadev.sparky_social_media.presentation.register_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.components.PrimaryButton
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.components.UserDataTextField
import ltd.bokadev.sparky_social_media.core.utils.Constants.INVALID_PASSWORD_LENGTH
import ltd.bokadev.sparky_social_media.core.utils.Constants.PASSWORD_CONTAIN_LOWERCASE
import ltd.bokadev.sparky_social_media.core.utils.Constants.PASSWORD_CONTAIN_NUMBER
import ltd.bokadev.sparky_social_media.core.utils.Constants.PASSWORD_CONTAIN_UPPERCASE
import ltd.bokadev.sparky_social_media.core.utils.CustomModifiers
import ltd.bokadev.sparky_social_media.domain.utils.isValidUsername
import ltd.bokadev.sparky_social_media.core.utils.observeWithLifecycle
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    showSnackBar: (message: String) -> Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    val state = viewModel.state

    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }


    Scaffold(topBar = { SparkyTopBar() }, bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 15.dp)
                .padding(horizontal = 20.dp)
        ) {
            PrimaryButton(
                text = stringResource(R.string.register),
                enabled = state.shouldEnableButton,
                color = if (state.shouldEnableButton) SparkyTheme.colors.yellow else SparkyTheme.colors.primaryColor,
                borderColor = if (state.shouldEnableButton) SparkyTheme.colors.yellow else SparkyTheme.colors.primaryColor,
                textColor = if (state.shouldEnableButton) SparkyTheme.colors.primaryColor else SparkyTheme.colors.white,
                textStyle = SparkyTheme.typography.poppinsMedium16,
                modifier = Modifier.height(50.dp)
            ) {
                viewModel.onEvent(RegisterEvent.OnRegisterClick)
            }
        }
    }) { innerPadding ->
        LazyColumn(modifier = Modifier.imePadding()) {
            item {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 20.dp)
                        .padding(top = 40.dp)
                ) {
                    Text(
                        text = stringResource(R.string.create_account),
                        color = SparkyTheme.colors.primaryColor,
                        style = SparkyTheme.typography.poppinsSemiBold24
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ClickableText(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = SparkyTheme.colors.textLightGray,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            ),
                        ) {
                            append(stringResource(R.string.have_an_account))
                        }

                        withStyle(
                            style = SpanStyle(
                                color = SparkyTheme.colors.yellow,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            )
                        ) {
                            append(stringResource(id = R.string.login))
                        }
                    },
                        onClick = { offset ->
                            viewModel.onEvent(RegisterEvent.OnLoginClick)
                        })
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.username),
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                        Text(
                            text = stringResource(R.string.from_3_and_20_characters),
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    UserDataTextField(
                        bringIntoViewRequester = bringIntoViewRequester,
                        scope = scope,
                        placeholderText = "Username",
                        value = state.username,
                        leadingIconId = R.drawable.ic_person,
                        trailingIconId = if (state.username.isNotEmpty() && state.username.isValidUsername()) R.drawable.ic_check else null,
                        keyboardOptions = CustomModifiers.emailKeyboard()
                    ) { viewModel.onEvent(RegisterEvent.UsernameChanged(it)) }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.email),
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                        Text(
                            text = stringResource(R.string.must_be_a_valid_email),
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    UserDataTextField(
                        bringIntoViewRequester = bringIntoViewRequester,
                        scope = scope,
                        placeholderText = "Email",
                        value = state.email,
                        trailingIconId = if (state.email.isNotEmpty() && state.emailError.isNotEmpty()) R.drawable.ic_check else null,
                        leadingIconId = R.drawable.ic_email,
                        keyboardOptions = CustomModifiers.emailKeyboard()
                    ) { viewModel.onEvent(RegisterEvent.EmailChanged(it)) }
                    Spacer(modifier = Modifier.height(16.dp))
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
                        onShowPasswordClick = { viewModel.onEvent(RegisterEvent.TogglePasswordVisibility) },
                        visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        value = state.password,
                        keyboardOptions = CustomModifiers.passwordKeyboardDone()
                    ) { viewModel.onEvent(RegisterEvent.PasswordChanged(it)) }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            painter = if (state.containsNineCharacters) painterResource(id = R.drawable.ic_check) else painterResource(
                                id = R.drawable.ic_cross
                            ), contentDescription = null, tint = Color.Unspecified
                        )
                        Text(
                            text = INVALID_PASSWORD_LENGTH,
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            painter = if (state.containsDigit) painterResource(id = R.drawable.ic_check) else painterResource(
                                id = R.drawable.ic_cross
                            ), contentDescription = null, tint = Color.Unspecified
                        )
                        Text(
                            text = PASSWORD_CONTAIN_NUMBER,
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            painter = if (state.containsLowercase) painterResource(id = R.drawable.ic_check) else painterResource(
                                id = R.drawable.ic_cross
                            ), contentDescription = null, tint = Color.Unspecified
                        )
                        Text(
                            text = PASSWORD_CONTAIN_LOWERCASE,
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            painter = if (state.containsUppercase) painterResource(id = R.drawable.ic_check) else painterResource(
                                id = R.drawable.ic_cross
                            ), contentDescription = null, tint = Color.Unspecified
                        )
                        Text(
                            text = PASSWORD_CONTAIN_UPPERCASE,
                            style = SparkyTheme.typography.poppinsRegular14,
                            color = SparkyTheme.colors.textLightGray
                        )
                    }


                }
            }
        }
    }
}