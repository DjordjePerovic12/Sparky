package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.Mocks.mockUserDetails
import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.CommentResponseDto
import ltd.bokadev.sparky_social_media.domain.model.Comment

fun List<CommentResponseDto>.toComments() : List<Comment> {
    return this.map {commentResponseDto ->
        Comment(
            id = commentResponseDto.id.toNonNull(),
            content = commentResponseDto.content.toNonNull(),
            createdAt = commentResponseDto.createdAt.toNonNull(),
            author = commentResponseDto.author?.toUserDetails() ?: mockUserDetails
        )
    }
}

fun CommentResponseDto.toComment() : Comment {
    return Comment(
        id = this.id.toNonNull(),
        content = this.content.toNonNull(),
        createdAt = this.createdAt.toNonNull(),
        author = this.author?.toUserDetails() ?: mockUserDetails
    )
}