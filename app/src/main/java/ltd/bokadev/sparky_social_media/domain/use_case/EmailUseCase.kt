package ltd.bokadev.sparky_social_media.domain.use_case

import ltd.bokadev.sparky_social_media.core.validation.ValidateEmail
import ltd.bokadev.sparky_social_media.core.validation.ValidationResult

class EmailUseCase(
    private val validateEmail: ValidateEmail
) {
    operator fun invoke(email: String): ValidationResult {
        return validateEmail.execute(email)
    }
}