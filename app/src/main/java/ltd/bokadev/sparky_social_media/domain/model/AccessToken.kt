package ltd.bokadev.sparky_social_media.domain.model

data class AccessToken(
    val accessToken: String, val expirationTimestamp: Long
)
