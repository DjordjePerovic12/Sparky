package ltd.bokadev.sparky_social_media.domain.use_case

import ltd.bokadev.sparky_social_media.core.validation.PasswordValidationResult
import ltd.bokadev.sparky_social_media.core.validation.ValidatePassword

class PasswordUseCase(private val validatePassword: ValidatePassword) {

    operator fun invoke(password: String): PasswordValidationResult {
        return validatePassword.execute(password)
    }
}