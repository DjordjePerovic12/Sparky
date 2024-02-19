package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginResponseDto
import ltd.bokadev.sparky_social_media.domain.model.UserData

fun LoginResponseDto.toUserData(): UserData {
    return UserData(
        id = this.userId.toNonNull(),
        username = this.username.toNonNull(),
        accessToken = this.accessToken.toNonNull(),
        refreshToken = this.refreshToken.toNonNull(),
        accessTokenExpirationTimestamp = this.accessTokenExpirationTimeStamp.toNonNull()
    )
}