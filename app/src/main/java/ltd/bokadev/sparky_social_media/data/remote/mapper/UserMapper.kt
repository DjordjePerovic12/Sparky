package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.Mocks.mockUserDetails
import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.UserDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserResponseDto
import ltd.bokadev.sparky_social_media.domain.model.RegistrationTime
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails


fun UserDto.toUserDetails(): UserDetails {
    return UserDetails(
        id = this.id.toNonNull(),
        profilePictureUrl = this.profilePictureUrl,
        username = this.username.toNonNull(),
        registeredAt = formatTime(this.registeredAt.toNonNull()).formattedTime.toNonNull(),
        followerCount = this.followerCount.toNonNull(),
        followingCount = this.followingCount.toNonNull(),
        postCount = this.postCount.toNonNull()
    )
}

fun UserResponseDto.toUser(): User {
    return User(
        user = this.user.toUserDetails(),
        isFollowing = this.isFollowing ?: false
    )
}

fun List<UserResponseDto>.toUsers(): List<User> {
    return this.map { userDto ->
        User(
            user = userDto.user.toUserDetails(),
            isFollowing = userDto.isFollowing ?: false
        )
    }
}

fun formatTime(utcTime: String): RegistrationTime {
    val zonedDateTime = RegistrationTime.convertToZonedDateTime(utcTime)
    val formattedTime = RegistrationTime.formatDateTime(zonedDateTime)
    return RegistrationTime(formattedTime = formattedTime)
}