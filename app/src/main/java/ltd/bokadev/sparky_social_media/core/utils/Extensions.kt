package ltd.bokadev.sparky_social_media.core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.bokadev.sparky_social_media.BuildConfig.API_KEY
import ltd.bokadev.sparky_social_media.BuildConfig.BASE_URL
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.Routes
import ltd.bokadev.sparky_social_media.core.navigation.Screen
import ltd.bokadev.sparky_social_media.core.utils.Constants.NO_INFO
import ltd.bokadev.sparky_social_media.core.validation.PasswordValidationResult
import okhttp3.Request
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String?.toNonNull() = if (this.isNullOrEmpty()) NO_INFO else this

fun Int?.toNonNull() = this ?: -1

fun Long?.toNonNull() = this ?: -1

fun Double?.toNonNull() = this ?: 0.0

infix fun Request.signWithToken(accessToken: String?): Request {
    val builder =
        newBuilder().header("Content-Type", "application/json").header("Accept", "application/json")
            .header("x-api-key", API_KEY)
    if (this.url.toString()
            .contains(BASE_URL) && !accessToken.isNullOrEmpty() && !this.url.encodedPath.contains(
            "register"
        )
    ) {
        builder.header("Authorization", "Bearer $accessToken")
    }
    return builder.build()
}

@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect(action)
        }
    }
}

private val LocalContextStringProvider = staticCompositionLocalOf<String?> { null }


fun String.isToken() = this != NO_INFO


suspend inline fun <T> Flow<Resource<T>>.collectLatestNoAuthCheck(
    crossinline onSuccess: suspend (Resource<T>) -> Unit,
    crossinline onError: suspend (Resource<T>) -> Unit,
) {
    collectLatest {
        when (it) {
            is Resource.Success -> onSuccess(it)
            is Resource.Error -> onError(it)
            else -> Unit
        }
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun String.getInitials(): String {
    val nameParts = this.split(' ')
    return when {
        nameParts.size == 1 -> {
            // If the username doesn't contain anything but a first name or a nickname
            //we want to take the first two chars as initials
            if (length >= 2) substring(0, 2) else this
        }

        // If the username contains both first and last name
        //we want to take the first char of both first and last name as initials
        nameParts.size >= 2 -> {
            // If there are multiple middle names, return the first letter of the first name
            // and the first letter of the last name
            val firstInitial = nameParts.firstOrNull()?.firstOrNull()?.toString() ?: ""
            val lastInitial = nameParts.lastOrNull()?.firstOrNull()?.toString() ?: ""
            firstInitial + lastInitial
        }

        else -> {
            ""
        }
    }
}

suspend inline fun <T> Flow<Resource<T>>.collectLatestWithAuthCheck(
    navigator: Navigator,
    crossinline onSuccess: suspend (Resource<T>) -> Unit,
    crossinline onError: suspend (Resource<T>) -> Unit,
) {
    withContext(NonCancellable) {
        collectLatest {
            when (it) {
                is Resource.Success -> onSuccess(it)
                is Resource.Error -> {
                    onError(it)
                    if (it.isUnauthorized == true) handleUnauthorized(navigator)
                    if (it.statusCode == 422) {
                        navigator.popUpTo(
                            route = Screen.LoginScreen.route,
                            staticRoute = Routes.AUTH,
                            inclusive = true
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}

suspend fun handleUnauthorized(navigator: Navigator) {
    navigator.popUpTo(
        route = Screen.LoginScreen.route, staticRoute = Routes.AUTH, inclusive = true
    )
}


fun Modifier.hideKeyboard(focusManager: FocusManager): Modifier = composed {
    pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}

fun String.formatToTwelveHourMonthNameDateTime(): String {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
        val parsedDate = formatter.parse(this) ?: return ""
        val calendar = Calendar.getInstance().apply {
            time = parsedDate
        }
        val formattedDate =
            SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(calendar.time)
        val formattedTime = SimpleDateFormat("h:mm a", Locale.getDefault()).format(calendar.time)
        "${formattedTime.uppercase()} - $formattedDate"
    } catch (e: Exception) {
        // Handle parsing exception
        e.printStackTrace()
        ""
    }
}