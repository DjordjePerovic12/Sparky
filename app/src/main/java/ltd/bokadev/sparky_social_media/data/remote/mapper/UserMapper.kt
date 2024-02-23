package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.UserDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserResponseDto
import ltd.bokadev.sparky_social_media.domain.model.RegistrationTime
import ltd.bokadev.sparky_social_media.domain.model.UserDetails

fun List<UserResponseDto>.toUsers(): List<UserDetails> {
    return this.map { userDto ->
        UserDetails(
            id = userDto.user?.id.toNonNull(),
            username = userDto.user?.username.toNonNull(),
            profilePictureUrl = userDto.user?.profilePictureUrl,
            registeredAt = formatTime(userDto.user?.registeredAt.toNonNull()).formattedTime.toNonNull(),
            postCount = userDto.user?.postCount.toNonNull(),
            followerCount = userDto.user?.followerCount.toNonNull(),
            followingCount = userDto.user?.followingCount.toNonNull(),
            isFollowing = userDto.user?.isFollowing ?: false
        )
    }
}

fun formatTime(utcTime: String): RegistrationTime {
    val zonedDateTime = RegistrationTime.convertToZonedDateTime(utcTime)
    val formattedTime = RegistrationTime.formatDateTime(zonedDateTime)
    return RegistrationTime(formattedTime = formattedTime)
}