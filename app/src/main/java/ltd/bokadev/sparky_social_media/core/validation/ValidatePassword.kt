package ltd.bokadev.sparky_social_media.core.validation

import dagger.hilt.android.scopes.ViewModelScoped
import ltd.bokadev.sparky_social_media.core.utils.Constants.EMPTY_FIELD
import ltd.bokadev.sparky_social_media.core.utils.isValidPassword

@ViewModelScoped
class ValidatePassword {
    fun execute(password: String): PasswordValidationResult {
            return password.isValidPassword()
    }
}