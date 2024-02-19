package ltd.bokadev.sparky_social_media.domain.model

data class UserData(
    val id: String,
    val username: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTimestamp: String
)
