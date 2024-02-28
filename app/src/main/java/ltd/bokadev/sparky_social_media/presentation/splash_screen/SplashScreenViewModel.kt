package ltd.bokadev.sparky_social_media.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes.AUTH
import ltd.bokadev.sparky_social_media.core.navigation.Routes.ROOT
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.isToken
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val navigator: Navigator, private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    init {
        handleStartDestination()
    }


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
                route = Screen.LoginScreen.route, staticRoute = AUTH, inclusive = true
            )
        }
    }

    private fun navigateToHomeScreen() {
        viewModelScope.launch {
            navigator.popUpTo(
                route = Screen.HomeScreen.route, staticRoute = ROOT, inclusive = true
            )
        }
    }


    private fun isLoggedIn() = runBlocking {
        withContext(Dispatchers.IO) {
            Timber.e("TOKEN ${dataStoreRepository.getToken().first()}")
            dataStoreRepository.getToken().first().isToken()
        }
    }

    private fun handleStartDestination() {
        if (isLoggedIn()) {
            navigateToHomeScreen()
        } else navigateToWelcomeScreen()
    }


}


sealed class SplashScreenEvent {
    data object NavigateToWelcomeScreen : SplashScreenEvent()
}