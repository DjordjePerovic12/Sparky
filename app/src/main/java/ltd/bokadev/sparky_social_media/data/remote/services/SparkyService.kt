package ltd.bokadev.sparky_social_media.data.remote.services

import ltd.bokadev.sparky_social_media.data.remote.dto.AccessTokenRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.AccessTokenResponseDto
import ltd.bokadev.sparky_social_media.data.remote.dto.CommentRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.CommentResponseDto
import ltd.bokadev.sparky_social_media.data.remote.dto.CreatePostRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.LoginResponseDto
import ltd.bokadev.sparky_social_media.data.remote.dto.PostIdRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.PostResponseDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserIdRequestDto
import ltd.bokadev.sparky_social_media.data.remote.dto.UserResponseDto
import ltd.bokadev.sparky_social_media.domain.model.Comment
import retrofit2.Call
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
    ): Response<List<UserResponseDto>>

    @POST("follow")
    suspend fun followUser(@Body userRequestDto: UserIdRequestDto): Response<Unit>


    @POST("unfollow")
    suspend fun unfollowUser(@Body userRequestDto: UserIdRequestDto): Response<Unit>

    @GET("feedPosts")
    suspend fun getFeedPosts(
        @Query("page") page: Int, @Query("pageCount") pageCount: Int
    ): Response<List<PostResponseDto>>

    @GET("comments")
    suspend fun getPostComments(
        @Query("postId") postId: String
    ): Response<List<CommentResponseDto>>

    @POST("comment")
    suspend fun addComment(@Body commentRequestDto: CommentRequestDto): Response<Any>

    @POST("like")
    suspend fun likePost(@Body postIdRequestDto: PostIdRequestDto): Response<Unit>

    @POST("unlike")
    suspend fun unlikePost(@Body postIdRequestDto: PostIdRequestDto): Response<Unit>

    @POST("accessToken")
    fun accessToken(@Body accessTokenRequestDto: AccessTokenRequestDto): Call<AccessTokenResponseDto>

    @GET("profile")
    suspend fun getProfileDetails(@Query("userId") userId: String?): Response<UserResponseDto>

    @GET("logout")
    suspend fun logout(): Response<Unit>
}