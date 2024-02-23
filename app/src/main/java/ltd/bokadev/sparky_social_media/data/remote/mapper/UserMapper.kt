package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.UserDto
import ltd.bokadev.sparky_social_media.domain.model.UserDetails

fun List<UserDto>.toUsers(): List<UserDetails> {
    return this.map { userDto ->
        UserDetails(
            id = userDto.id.toNonNull(),
            username = userDto.username.toNonNull(),
            profilePictureUrl = userDto.profilePictureUrl.toNonNull(),
            registeredAt = userDto.registeredAt.toNonNull(),
            postCount = userDto.postCount.toNonNull(),
            followerCount = userDto.followerCount.toNonNull(),
            followingCount = userDto.followingCount.toNonNull()
        )
    }
}