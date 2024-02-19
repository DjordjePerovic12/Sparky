package ltd.bokadev.sparky_social_media.core.base

import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ltd.bokadev.sparky_social_media.core.utils.Constants.CHECK_CONNECTION
import ltd.bokadev.sparky_social_media.core.utils.Constants.NETWORK_PROBLEM
import ltd.bokadev.sparky_social_media.core.utils.Constants.UNAUTHORIZED
import ltd.bokadev.sparky_social_media.core.utils.Resource
import ltd.bokadev.sparky_social_media.core.utils.toNonNull
import ltd.bokadev.sparky_social_media.data.remote.dto.ApiErrorDto
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

open class BaseDataSource constructor(
    private val errorAdapter: JsonAdapter<ApiErrorDto>
) {
    protected suspend fun <T> retrieveFlow(
        call: suspend () -> Response<T>
    ) = flow {
        emit(Resource.Loading())
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                emit(Resource.Success(data = body, statusCode = response.code()))
                Timber.d("Successful response: $body")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = errorAdapter.fromJson(errorBody.toNonNull())?.message
                if (response.code() == 401) {
                    emit(Resource.Error(message = UNAUTHORIZED))
                    Timber.e("Bad response: $errorMessage")
                } else {
                    emit(Resource.Error(message = errorMessage, statusCode = response.code()))
                    Timber.e("Bad response: $errorMessage")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is IOException) {
                Timber.e("Bad response: ${e.message}")
                emit(Resource.Error(message = CHECK_CONNECTION))
            } else {
                Timber.e("Bad response: ${e.message}")
                emit(Resource.Error(message = NETWORK_PROBLEM))
            }
        }
    }.flowOn(Dispatchers.IO)

    fun <T : Any, E : Any> Flow<Resource<T>>.mapResponse(mapperCallback: T.() -> E) = this.map {
        when (it) {
            is Resource.Success -> Resource.Success(
                data = it.data?.mapperCallback(), statusCode = it.statusCode
            )

            is Resource.Error -> Resource.Error(
                message = it.message, statusCode = it.statusCode
            )

            else -> Resource.Loading()
        }

    }
}

