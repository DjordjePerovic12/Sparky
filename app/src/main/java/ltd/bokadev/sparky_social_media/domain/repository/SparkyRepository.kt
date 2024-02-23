package ltd.bokadev.sparky_social_media.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.data.UsersPagingSource
import ltd.bokadev.sparky_social_media.data.remote.dto.CreatePostRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.domain.model.Post
import ltd.bokadev.sparky_social_media.domain.model.PostRequest
import ltd.bokadev.sparky_social_media.domain.model.UserData
import ltd.bokadev.sparky_social_media.domain.model.UserDetails

interface SparkyRepository {
    suspend fun register(registrationRequestDto: RegistrationRequestDto): Flow<Resource<Unit>>
    suspend fun login(loginRequestDto: LoginRequestDto): Flow<Resource<UserData>>
    suspend fun createPost(postRequest: PostRequest): Flow<Resource<Post>>
    suspend fun searchProfiles(
        searchQuery: String,
        pageCount: Int
    ): Flow<PagingData<UserDetails>>
}