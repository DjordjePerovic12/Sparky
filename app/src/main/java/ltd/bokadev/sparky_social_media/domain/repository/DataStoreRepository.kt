package ltd.bokadev.sparky_social_media.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveToken(token: String)

    suspend fun getToken(): Flow<String>

    suspend fun clearDatastore()

}