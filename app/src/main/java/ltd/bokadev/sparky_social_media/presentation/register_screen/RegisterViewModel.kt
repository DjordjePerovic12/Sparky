package ltd.bokadev.sparky_social_media.presentation.register_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.collectLatestNoAuthCheck
import ltd.bokadev.sparky_social_media.core.utils.isValidEmail
import ltd.bokadev.sparky_social_media.core.utils.isValidPassword
import ltd.bokadev.sparky_social_media.core.utils.isValidUsername
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.domain.use_case.EmailUseCase
import ltd.bokadev.sparky_social_media.domain.use_case.PasswordUseCase
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val sparkyRepository: SparkyRepository,
    private val navigator: Navigator,
    private val emailUseCase: EmailUseCase,
    private val passwordUseCase: PasswordUseCase,
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val _snackBarChannel = Channel<String>()
    val snackBarChannel = _snackBarChannel.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UsernameChanged -> {
                state = state.copy(username = event.username.trim())
                isUserDataValid()
            }

            is RegisterEvent.EmailChanged -> {
                state = state.copy(email = event.email.trim())
                validateEmail()
                isUserDataValid()
            }

            is RegisterEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                validatePassword()
                isUserDataValid()
            }

            is RegisterEvent.OnLoginClick -> {
                navigateToLogin()
            }

            is RegisterEvent.OnRegisterClick -> {
                executeRegister()
            }

            is RegisterEvent.TogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
        }
    }


    private fun validateEmail() {
        val emailResult = emailUseCase.invoke(state.email)
        val emailHasError = !emailResult.successful
        if (emailHasError) {
            state = state.copy(emailError = emailResult.errorMessage ?: "")
            return
        } else state = state.copy(emailError = "")
    }

    private fun validatePassword() {
        val passwordResult = passwordUseCase.invoke(state.password)
        state = state.copy(
            containsNineCharacters = passwordResult.hasNineCharacters,
            containsUppercase = passwordResult.containsUppercase,
            containsLowercase = passwordResult.containsLowercase,
            containsDigit = passwordResult.containsDigit
        )
    }

    private fun isUserDataValid() {
        state =
            if (state.username.isValidUsername() && state.email.isValidEmail() && isValidPassword()) state.copy(
                shouldEnableButton = true
            ) else state.copy(shouldEnableButton = false)
    }

    private fun isValidPassword(): Boolean {
        return state.containsNineCharacters && state.containsLowercase && state.containsUppercase && state.containsDigit
    }

    private fun executeRegister() {
        viewModelScope.launch {
            sparkyRepository.register(
                RegistrationRequestDto(
                    username = state.username, password = state.password, email = state.email
                )
            ).collectLatestNoAuthCheck(
                onSuccess = {
                    navigateToLogin()
                    _snackBarChannel.send("Successfully registered! Please log in!")
                },
                onError = {
                    _snackBarChannel.send("Error registering!")
                }
            )
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            navigator.navigateTo(Screen.LoginScreen.route)
        }
    }

}

data class RegisterState(
    val username: String = String(),
    val password: String = String(),
    val email: String = String(),
    val emailError: String = String(),
    val containsNineCharacters: Boolean = false,
    val containsDigit: Boolean = false,
    val containsUppercase: Boolean = false,
    val containsLowercase: Boolean = false,
    val shouldEnableButton: Boolean = false,
    val isPasswordVisible: Boolean = false
)

sealed class RegisterEvent {
    data object OnRegisterClick : RegisterEvent()
    data object OnLoginClick : RegisterEvent()
    data class UsernameChanged(val username: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
}

