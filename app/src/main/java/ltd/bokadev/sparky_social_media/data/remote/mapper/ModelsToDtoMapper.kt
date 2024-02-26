package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.CreatePostRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserIdRequestDto
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.UserIdRequest


fun PostRequest.toDto(): CreatePostRequestDto {
    return CreatePostRequestDto(content = this.content.toNonNull())
}

fun UserIdRequest.toDto(): UserIdRequestDto {
    return UserIdRequestDto(userId = this.userId.toNonNull())
}