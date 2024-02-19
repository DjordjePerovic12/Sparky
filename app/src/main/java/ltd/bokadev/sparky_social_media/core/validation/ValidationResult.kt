package ltd.bokadev.sparky_social_media.core.validation

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
