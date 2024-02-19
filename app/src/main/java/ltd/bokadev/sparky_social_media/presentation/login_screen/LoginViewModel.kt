package ltd.bokadev.sparky_social_media.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.presentation.register_screen.RegisterEvent
import ltd.bokadev.sparky_social_media.presentation.register_screen.RegisterState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: SparkyRepository, private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnRegisterClick -> {
                navigateToRegisterScreen()
            }

            is LoginEvent.OnLoginClick -> {

            }

            is LoginEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email
                )
                enableLogin()
            }

            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                enableLogin()
            }

            is LoginEvent.TogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
        }
    }

    private fun navigateToRegisterScreen() {
        viewModelScope.launch {
            navigator.navigateTo(Screen.RegisterScreen.route)
        }
    }

    private fun enableLogin() {
        state = if (state.email.isNotEmpty() && state.password.isNotEmpty()) state.copy(
            shouldEnableLogin = true
        ) else state.copy(shouldEnableLogin = false)
    }

}


sealed class LoginEvent {
    data object OnLoginClick : LoginEvent()
    data object OnRegisterClick : LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val shouldEnableLogin: Boolean = false
)