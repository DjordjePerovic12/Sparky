package ltd.bokadev.sparky_social_media.core.utils

import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserData
import ltd.bokadev.sparky_social_media.domain.model.UserDetails

object Mocks {
    val mockUserData = UserData(
        id = "",
        accessToken = "",
        accessTokenExpirationTimestamp = "",
        refreshToken = "",
        username = ""
    )

    val mockUserDetails = UserDetails(

            id = "",
            username = "",
            profilePictureUrl = "",
            registeredAt = "",
            postCount = 12,
            followingCount = 12,
            followerCount = 12
    )
}