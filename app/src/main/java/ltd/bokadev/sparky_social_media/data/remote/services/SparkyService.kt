package ltd.bokadev.sparky_social_media.data.remote.services

import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SparkyService {
    @POST("register")
    suspend fun register(@Body registrationRequestDto: RegistrationRequestDto): Response<Unit>
}