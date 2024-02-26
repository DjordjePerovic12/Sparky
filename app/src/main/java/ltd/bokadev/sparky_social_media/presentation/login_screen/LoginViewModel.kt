package ltd.bokadev.sparky_social_media.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.ROOT
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Mocks.mockUserData
import ltd.bokadev.sparky_social_media.core.utils.collectLatestNoAuthCheck
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.domain.model.UserData
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: SparkyRepository,
    private val navigator: Navigator,
    private val dataStoreRepository: DataStoreRepository
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
                executeLogin()
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

    private fun executeLogin() {
        viewModelScope.launch {
            repository.login(LoginRequestDto(email = state.email, password = state.password))
                .collectLatestNoAuthCheck(onSuccess = { result ->
                    saveData(result.data ?: mockUserData)
                    navigateToHomeScreen()
                }, onError = { result ->
                    _snackBarChannel.send(result.message ?: "Error login in user.")
                })
        }
    }

    /** We need to save user data before proceeding.
     * Using runBlocking to block current thread until its completion.
     * Switching to IO thread in order to free up Main thread.*/
    private fun saveData(user: UserData) = runBlocking {
        withContext(Dispatchers.IO) {
            dataStoreRepository.saveUser(
                user = user
            )
        }
    }

    private fun navigateToHomeScreen() {
        viewModelScope.launch {
            navigator.popUpTo(route = Screen.HomeScreen.route, staticRoute = ROOT, inclusive = true)
        }
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