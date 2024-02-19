package ltd.bokadev.sparky_social_media.core.validation

data class PasswordValidationResult(
    val hasNineCharacters: Boolean,
    val containsDigit: Boolean,
    val containsUppercase: Boolean,
    val containsLowercase: Boolean
)
