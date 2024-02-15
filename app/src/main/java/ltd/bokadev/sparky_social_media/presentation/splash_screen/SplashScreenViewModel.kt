package ltd.bokadev.sparky_social_media.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.AUTH
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: SplashScreenEvent) {
        when (event) {
            is SplashScreenEvent.NavigateToWelcomeScreen -> {
                navigateToWelcomeScreen()
            }
        }
    }

    private fun navigateToWelcomeScreen() {
        viewModelScope.launch {
            navigator.popUpTo(
                route = Screen.WelcomeScreen.route, staticRoute = AUTH, inclusive = true
            )
        }
    }


}


sealed class SplashScreenEvent {
    data object NavigateToWelcomeScreen : SplashScreenEvent()
}