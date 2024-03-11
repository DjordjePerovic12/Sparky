package ltd.bokadev.sparky_social_media.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.utils.PostFilters
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.model.NotificationWrapper
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.PostIdRequest
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserData
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.domain.model.UserIdRequest
import okhttp3.MultipartBody

interface SparkyRepository {
    suspend fun register(registrationRequestDto: RegistrationRequestDto): Resource<Unit>
    suspend fun login(loginRequestDto: LoginRequestDto): Resource<UserData>
    suspend fun createPost(postRequest: PostRequest): Resource<Post>
    suspend fun searchProfiles(
        searchQuery: String, pageCount: Int
    ): Flow<PagingData<User>>

    suspend fun followUser(userIdRequest: UserIdRequest): Resource<Unit>
    suspend fun unfollowUser(userIdRequest: UserIdRequest): Resource<Unit>
    suspend fun getFeedPosts(pageCount: Int): Flow<PagingData<Post>>
    suspend fun getPostComments(postId: String): Resource<List<Comment>>
    suspend fun addComment(commentRequest: CommentRequest): Resource<Any>
    suspend fun likePost(postIdRequest: PostIdRequest): Resource<Unit>
    suspend fun unlikePost(postIdRequest: PostIdRequest): Resource<Unit>
    suspend fun getProfileDetails(userId: String?): Resource<User>
    suspend fun logout(): Resource<Unit>
    suspend fun changeProfilePicture(profilePicture: MultipartBody.Part): Resource<UserDetails>
    fun getProfilePosts(
        userId: String?,
        pageCount: Int,
        postsFilter: PostFilters
    ): Flow<PagingData<Post>>

    fun getLikedPosts(
        userId: String?,
        pageCount: Int,
        postsFilter: PostFilters
    ): Flow<PagingData<Post>>

    fun getNotifications(
        pageCount: Int
    ): Flow<PagingData<NotificationWrapper>>
}