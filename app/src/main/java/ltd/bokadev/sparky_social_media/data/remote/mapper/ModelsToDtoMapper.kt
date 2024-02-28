package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.AccessTokenRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.CommentRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.CreatePostRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.PostIdRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserIdRequestDto
import ltd.bokadev.sparky_social_media.domain.model.AccessTokenRequest
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.model.PostIdRequest
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.UserIdRequest


fun PostRequest.toDto(): CreatePostRequestDto {
    return CreatePostRequestDto(content = this.content.toNonNull())
}

fun UserIdRequest.toDto(): UserIdRequestDto {
    return UserIdRequestDto(userId = this.userId.toNonNull())
}

fun CommentRequest.toDto(): CommentRequestDto {
    return CommentRequestDto(
        postId = this.postId.toNonNull(), content = this.content.toNonNull()
    )
}

fun PostIdRequest.toDto(): PostIdRequestDto {
    return PostIdRequestDto(
        postId = this.postId.toNonNull()
    )
}

fun AccessTokenRequest.toDto(): AccessTokenRequestDto {
    return AccessTokenRequestDto(
        userId = this.userId.toNonNull(), refreshToken = this.refreshToken.toNonNull()
    )
}