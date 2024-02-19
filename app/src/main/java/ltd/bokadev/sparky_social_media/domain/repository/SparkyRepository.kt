package ltd.bokadev.sparky_social_media.domain.repository

import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto

interface SparkyRepository {
    suspend fun register(registrationRequestDto: RegistrationRequestDto) : Flow<Resource<Unit>>
}