package ltd.bokadev.sparky_social_media.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay
import ltd.bokadev.sparky_social_media.R
import ltd.bokadev.sparky_social_media.ui.theme.SparkyTheme

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel
) {

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        viewModel.onEvent(SplashScreenEvent.NavigateToWelcomeScreen)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SparkyTheme.colors.primaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.sparky_logo), contentDescription = null)

        Text(
            text = stringResource(R.string.sparky),
            style = SparkyTheme.typography.poppinsMedium32,
            color = SparkyTheme.colors.white
        )
    }
}