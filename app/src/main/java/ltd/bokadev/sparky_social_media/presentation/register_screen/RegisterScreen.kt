package ltd.bokadev.sparky_social_media.presentation.register_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.components.PrimaryButton
import ltd.bokadev.sparky_social_media.core.components.SparkyTopBar
import ltd.bokadev.sparky_social_media.core.components.UserDataTextField
import ltd.bokadev.sparky_social_media.presentation.login_screen.LoginBottomBar
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen() {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

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
                //TODO: Handle colors when button is disabled
                color = SparkyTheme.colors.yellow,
                borderColor = SparkyTheme.colors.yellow,
                textColor = SparkyTheme.colors.primaryColor,
                textStyle = SparkyTheme.typography.poppinsMedium16,
                modifier = Modifier.height(50.dp)
            ) {

            }
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
                text = stringResource(R.string.create_account),
                color = SparkyTheme.colors.primaryColor,
                style = SparkyTheme.typography.poppinsSemiBold24
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = SparkyTheme.colors.textLightGray,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                ) {
                    append(stringResource(R.string.have_an_account))
                }

                withStyle(
                    style = SpanStyle(
                        color = SparkyTheme.colors.yellow,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                    )
                ) {
                    append(stringResource(id = R.string.login))
                }
            })
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                leadingIconId = R.drawable.ic_person
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                leadingIconId = R.drawable.ic_email
            )
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
                trailingIconId = R.drawable.ic_show_password
            )
        }
    }
}