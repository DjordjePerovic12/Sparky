package ltd.bokadev.sparky_social_media.data.remote.mapper

import ltd.bokadev.sparky_social_media.core.utils.Mocks.mockUserData
import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.PostResponseDto
import ltd.bokadev.sparky_social_media.domain.model.Post

fun PostResponseDto.toPost(): Post {
    return Post(
        id = this.id.toNonNull(),
        content = this.content.toNonNull(),
        createdAt = this.createdAt.toNonNull(),
        likeCount = this.likeCount.toNonNull(),
        commentCount = this.commentCount.toNonNull(),
        isLiked = this.isLiked ?: false,
        author = this.author?.toUserData() ?: mockUserData
    )
}
