package ltd.bokadev.sparky_social_media.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ltd.bokadev.sparky_social_media.BuildConfig.BASE_URL
import ltd.bokadev.sparky_social_media.core.utils.Constants.NO_INFO
import ltd.bokadev.sparky_social_media.core.validation.PasswordValidationResult
import okhttp3.Request

fun String?.toNonNull() = if (this.isNullOrEmpty()) NO_INFO else this

fun Int?.toNonNull() = this ?: -1

fun Double?.toNonNull() = this ?: 0.0

infix fun Request.signWithToken(accessToken: String?): Request {
    val builder = newBuilder().header("Accept", "application/json")
    if (this.url.toString()
            .contains(BASE_URL) && !accessToken.isNullOrEmpty() && !this.url.encodedPath.contains(
            "login"
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


fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
    return matches(emailRegex)
}

fun String.isValidPassword(): PasswordValidationResult {
    val hasNineCharacters = this.length >= 9
    val containsLowerCase = any { it.isLowerCase() }
    val containsUpperCase = any { it.isUpperCase() }
    val containsDigit = any { it.isDigit() }
    return PasswordValidationResult(
        hasNineCharacters = hasNineCharacters,
        containsDigit = containsDigit,
        containsLowercase = containsLowerCase,
        containsUppercase = containsUpperCase
    )
}
