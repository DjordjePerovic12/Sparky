package ltd.bokadev.sparky_social_media.data.remote.services

import ltd.bokadev.sparky_social_media.data.remote.dto.CreatePostRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginResponseDto
import ltd.bokadev.sparky_social_media.data.remote.dto.PostResponseDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SparkyService {
    @POST("register")
    suspend fun register(@Body registrationRequestDto: RegistrationRequestDto): Response<Unit>

    @POST("login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<LoginResponseDto>


    @POST("post")
    suspend fun createPost(@Body createPostRequestDto: CreatePostRequestDto): Response<PostResponseDto>

    @GET("searchProfiles")
    suspend fun searchProfiles(
        @Query("query") searchQuery: String,
        @Query("page") page: Int,
        @Query("pageCount") pageCount: Int
    ) : Response<List<UserResponseDto>>
}