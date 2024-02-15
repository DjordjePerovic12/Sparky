package ltd.bokadev.sparky_social_media.presentation.welcome_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.core.components.PrimaryButton
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(SparkyTheme.colors.primaryColor)
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.sparky_logo), contentDescription = null
            )

            Text(
                text = stringResource(R.string.sparky),
                style = SparkyTheme.typography.poppinsMedium32,
                color = SparkyTheme.colors.white
            )
        }
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(SparkyTheme.colors.primaryColor)
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp,
                    vertical = 100.dp
                ),
        ) {
            Text(
                text = stringResource(R.string.welcome_to_our_sparky_social_media_app),
                style = SparkyTheme.typography.poppinsMedium24,
                color = SparkyTheme.colors.white,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.ignite_your_conversations_and_illuminate_your_world),
                style = SparkyTheme.typography.poppinsRegular14,
                color = SparkyTheme.colors.textSecondaryColor,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                modifier = Modifier
                    .height(50.dp),
                text = stringResource(R.string.sign_in),
                color = SparkyTheme.colors.primaryColor,
                borderColor = SparkyTheme.colors.white,
                textColor = SparkyTheme.colors.white,
                textStyle = SparkyTheme.typography.poppinsMedium16
            ) {

            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                modifier = Modifier
                    .height(50.dp),
                text = stringResource(R.string.sign_up),
                color = SparkyTheme.colors.yellow,
                borderColor = SparkyTheme.colors.yellow,
                textColor = SparkyTheme.colors.primaryColor,
                textStyle = SparkyTheme.typography.poppinsMedium16
            ) {

            }

        }
    }
}