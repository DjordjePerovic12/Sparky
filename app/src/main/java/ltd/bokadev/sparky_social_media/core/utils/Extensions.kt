package ltd.bokadev.sparky_social_media.core.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.BuildConfig.API_KEY
import ltd.bokadev.sparky_social_media.BuildConfig.BASE_URL
import ltd.bokadev.sparky_social_media.core.utils.Constants.NO_INFO
import ltd.bokadev.sparky_social_media.core.validation.PasswordValidationResult
import okhttp3.Request
import timber.log.Timber

fun String?.toNonNull() = if (this.isNullOrEmpty()) NO_INFO else this

fun Int?.toNonNull() = this ?: -1

fun Double?.toNonNull() = this ?: 0.0

infix fun Request.signWithToken(accessToken: String?): Request {
    val builder = newBuilder().header("Content-Type", "application/json").header("Accept", "application/json").header("x-api-key", API_KEY)
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

@Composable
fun Int.getStringLength(): Int {
    val context = LocalContext.current
    // Retrieve the string value from the resource ID
    val stringValue = context.getString(this)
    // Provide the string value via CompositionLocal
    CompositionLocalProvider(LocalContextStringProvider provides stringValue) {
        // Return the length of the string
        stringValue.length
    }
    return stringValue.length
}


fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
    return matches(emailRegex)
}

fun String.isValidPassword(): PasswordValidationResult {
    val hasNineCharacters = this.length >= 9
    Timber.e("PASSWORD HAS9 $hasNineCharacters")
    val containsLowerCase = any { it.isLowerCase() }
    Timber.e("PASSWORD LOW $containsLowerCase")
    val containsUpperCase = any { it.isUpperCase() }
    Timber.e("PASSWORD upp $containsUpperCase")
    val containsDigit = any { it.isDigit() }
    Timber.e("PASSWORD dig $containsDigit")


    return PasswordValidationResult(
        hasNineCharacters = hasNineCharacters,
        containsDigit = containsDigit,
        containsLowercase = containsLowerCase,
        containsUppercase = containsUpperCase
    )
}


fun String.isValidUsername(): Boolean {
    return length in 3..20
}

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
