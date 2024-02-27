package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.AccessTokenResponseDto
import ltd.bokadev.sparky_social_media.domain.model.AccessToken

fun AccessTokenResponseDto.toAccessToken(): AccessToken {
    return AccessToken(
        accessToken = this.accessToken.toNonNull(),
        expirationTimestamp = this.expirationTimeStamp.toNonNull()
    )
}