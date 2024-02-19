package ltd.bokadev.sparky_social_media.core.validation

import dagger.hilt.android.scopes.ViewModelScoped
import ltd.bokadev.sparky_social_media.core.utils.Constants.EMAIL_INVALID
import ltd.bokadev.sparky_social_media.core.utils.Constants.EMPTY_FIELD
import ltd.bokadev.sparky_social_media.core.utils.isValidEmail

@ViewModelScoped
class ValidateEmail {
    fun execute(text: String): ValidationResult {
        return if (text.isEmpty()) {
            ValidationResult(
                successful = false, errorMessage = EMPTY_FIELD
            )
        } else if (!text.isValidEmail()) ValidationResult(
            successful = false, errorMessage = EMAIL_INVALID
        )
        else ValidationResult(successful = true)
    }
}