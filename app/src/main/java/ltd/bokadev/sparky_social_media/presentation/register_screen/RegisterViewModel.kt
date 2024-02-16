package ltd.bokadev.sparky_social_media.presentation.register_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.validation.PasswordValidationResult
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.domain.use_case.EmailUseCase
import ltd.bokadev.sparky_social_media.domain.use_case.PasswordUseCase
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

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UsernameChanged -> {
                state = state.copy(username = event.username.trim())
            }

            is RegisterEvent.EmailChanged -> {
                state = state.copy(email = event.email.trim())
            }

            is RegisterEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                validatePassword()
            }
        }
    }


    private fun validateEmail() {
        val emailResult = emailUseCase.invoke(state.email)
        val emailHasError = !emailResult.successful
        if (emailHasError) {
            state = state.copy(emailError = emailResult.errorMessage ?: "")
            return
        }
    }

    private fun validatePassword() {
        val passwordResult = passwordUseCase.invoke(state.password)
        val passwordProperties = PasswordValidationResult::class.java.declaredFields
        val passwordHasError = passwordProperties.any {
            it.isAccessible = true
            it.get(passwordResult) == false
        }

        if (passwordHasError) {
            state = state.copy(
                containsNineCharacters = passwordResult.hasNineCharacters,
                containsUppercase = passwordResult.containsUppercase,
                containsLowercase = passwordResult.containsLowercase,
                containsDigit = passwordResult.containsDigit
            )
            return
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
    val containsLowercase: Boolean = false
)

sealed class RegisterEvent {
//    data class OnRegisterClick(
//        val userName: String, val email: String, val password: String
//    ) : RegisterEvent()

    data class UsernameChanged(val username: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
}

