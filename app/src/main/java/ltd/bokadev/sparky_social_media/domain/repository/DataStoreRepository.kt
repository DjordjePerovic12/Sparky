package ltd.bokadev.sparky_social_media.domain.repository

import kotlinx.coroutines.flow.Flow
import ltd.bokadev.sparky_social_media.domain.model.UserData

interface DataStoreRepository {

    suspend fun saveToken(token: String)

    suspend fun saveUser(user: UserData)

    suspend fun getUser(): Flow<UserData>

    suspend fun getToken(): Flow<String>

    suspend fun clearDatastore()

}