package ltd.bokadev.sparky_social_media.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.base.BaseDataSource
import ltd.bokadev.sparky_social_media.data.paging_source.PostsPagingSource
import ltd.bokadev.sparky_social_media.data.paging_source.UsersPagingSource
import ltd.bokadev.sparky_social_media.data.remote.dto.ApiErrorDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.data.remote.mapper.toDto
import ltd.bokadev.sparky_social_media.data.remote.mapper.toPost
import ltd.bokadev.sparky_social_media.data.remote.mapper.toUserData
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.User
import ltd.bokadev.sparky_social_media.domain.model.UserIdRequest
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SparkyRepositoryImpl @Inject constructor(
    private val sparkyService: SparkyService, errorAdapter: JsonAdapter<ApiErrorDto>
) : SparkyRepository, BaseDataSource(errorAdapter) {
    override suspend fun register(registrationRequestDto: RegistrationRequestDto) = retrieveFlow {
        sparkyService.register(registrationRequestDto)
    }

    override suspend fun login(loginRequestDto: LoginRequestDto) = retrieveFlow {
        sparkyService.login(loginRequestDto)
    }.mapResponse { toUserData() }


    override suspend fun createPost(postRequest: PostRequest) = retrieveFlow {
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

    override suspend fun followUser(userIdRequest: UserIdRequest) = retrieveFlow {
        val userIdRequestDto = userIdRequest.toDto()
        sparkyService.followUser(userIdRequestDto)
    }

    override suspend fun unfollowUser(userIdRequest: UserIdRequest) = retrieveFlow {
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
}


