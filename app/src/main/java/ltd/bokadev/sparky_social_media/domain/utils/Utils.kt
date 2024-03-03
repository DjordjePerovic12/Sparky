package ltd.bokadev.sparky_social_media.domain.utils

import ltd.bokadev.sparky_social_media.core.validation.PasswordValidationResult
import timber.log.Timber


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