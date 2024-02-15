package ltd.bokadev.sparky_social_media.presentation.welcome_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    fun onEvent(event: WelcomeScreenEvent) {
        when (event) {
            is WelcomeScreenEvent.OnSignUpClick -> {
                navigateToRegisterScreen()
            }

            is WelcomeScreenEvent.OnSignInClick -> {
                navigateToLoginScreen()
            }
        }
    }


    private fun navigateToLoginScreen() {
        viewModelScope.launch {
            navigator.navigateTo(Screen.LoginScreen.route)
        }
    }

    private fun navigateToRegisterScreen() {
        viewModelScope.launch {
            navigator.navigateTo(Screen.RegisterScreen.route)
        }
    }


}


sealed class WelcomeScreenEvent {
    data object OnSignUpClick : WelcomeScreenEvent()
    data object OnSignInClick : WelcomeScreenEvent()
}