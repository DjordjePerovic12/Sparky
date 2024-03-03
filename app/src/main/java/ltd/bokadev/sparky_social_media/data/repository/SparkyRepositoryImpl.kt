package ltd.bokadev.sparky_social_media.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.base.BaseDataSource
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.data.paging_source.PostsPagingSource
import ltd.bokadev.sparky_social_media.data.paging_source.UsersPagingSource
import ltd.bokadev.sparky_social_media.data.remote.dto.ApiErrorDto
import ltd.bokadev.sparky_social_media.data.remote.dto.CommentRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.data.remote.mapper.toAccessToken
import ltd.bokadev.sparky_social_media.data.remote.mapper.toComments
import ltd.bokadev.sparky_social_media.data.remote.mapper.toDto
import ltd.bokadev.sparky_social_media.data.remote.mapper.toPost
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUser
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUserData
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUserDetails
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.model.AccessToken
import ltd.bokadev.sparky_social_media.domain.model.AccessTokenRequest
import ltd.bokadev.sparky_social_media.domain.model.Comment
import ltd.bokadev.sparky_social_media.domain.model.CommentRequest
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.PostIdRequest
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserDetails
import ltd.bokadev.sparky_social_media.domain.model.UserIdRequest
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton


//Will refactor the ones including pagination in another PR, when I research it a bit better
@Singleton
class SparkyRepositoryImpl @Inject constructor(
    private val sparkyService: SparkyService, errorAdapter: JsonAdapter<ApiErrorDto>
) : SparkyRepository, BaseDataSource(errorAdapter) {
    override suspend fun register(registrationRequestDto: RegistrationRequestDto) =
        retrieveResponse {
            sparkyService.register(registrationRequestDto)
        }

    override suspend fun login(loginRequestDto: LoginRequestDto) = retrieveResponse {
        sparkyService.login(loginRequestDto)
    }.mapResponse { toUserData() }


    override suspend fun createPost(postRequest: PostRequest) = retrieveResponse {
        val postRequestDto = postRequest.toDto()
        sparkyService.createPost(postRequestDto)
    }.mapResponse { toPost() }


    override suspend fun searchProfiles(
        searchQuery: String, pageCount: Int
    ): Flow<PagingData<User>> = Pager(
        PagingConfig(
            pageSize = pageCount, prefetchDistance = 1, enablePlaceholders = false
        )
    ) {
        UsersPagingSource(searchQuery = searchQuery, sparkyService = sparkyService)
    }.flow

    override suspend fun followUser(userIdRequest: UserIdRequest) = retrieveResponse {
        val userIdRequestDto = userIdRequest.toDto()
        sparkyService.followUser(userIdRequestDto)
    }

    override suspend fun unfollowUser(userIdRequest: UserIdRequest) = retrieveResponse {
        val userIdRequestDto = userIdRequest.toDto()
        sparkyService.unfollowUser(userIdRequestDto)
    }

    override suspend fun getFeedPosts(pageCount: Int): Flow<PagingData<Post>> = Pager(
        PagingConfig(
            pageSize = pageCount, prefetchDistance = 1, enablePlaceholders = false
        )
    ) {
        PostsPagingSource(
            sparkyService = sparkyService
        )
    }.flow

    override suspend fun getPostComments(postId: String) = retrieveResponse {
        sparkyService.getPostComments(postId)
    }.mapResponse { toComments() }

    override suspend fun addComment(commentRequest: CommentRequest) = retrieveResponse {
        val commentRequestDto = commentRequest.toDto()
        sparkyService.addComment(commentRequestDto)
    }

    override suspend fun likePost(postIdRequest: PostIdRequest) = retrieveResponse {
        val postIdRequestDto = postIdRequest.toDto()
        sparkyService.likePost(postIdRequestDto = postIdRequestDto)
    }

    override suspend fun unlikePost(postIdRequest: PostIdRequest) = retrieveResponse {
        val postIdRequestDto = postIdRequest.toDto()
        sparkyService.unlikePost(postIdRequestDto = postIdRequestDto)
    }

    override suspend fun getProfileDetails(userId: String?) = retrieveResponse {
        sparkyService.getProfileDetails(userId)
    }.mapResponse { toUser() }

    override suspend fun logout() = retrieveResponse {
        sparkyService.logout()
    }

    override suspend fun changeProfilePicture(profilePicture: MultipartBody.Part) =
        retrieveResponse {
            sparkyService.changeProfilePicture(profilePicture = profilePicture)
        }.mapResponse { toUserDetails() }
}


