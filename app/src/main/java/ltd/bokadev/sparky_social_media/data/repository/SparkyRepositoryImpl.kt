package ltd.bokadev.sparky_social_media.data.repository

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.core.base.BaseDataSource
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.data.remote.dto.ApiErrorDto
import ltd.bokadev.sparky_social_media.data.remote.dto.RegistrationRequestDto
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
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
}


