package ltd.bokadev.sparky_social_media.domain.model

data class AccessTokenRequest(
    val userId: String,
    val refreshToken: String
)
