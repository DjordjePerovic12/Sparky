package ltd.bokadev.sparky_social_media.core.utils

sealed class Resource<T>(
    val data: T? = null, val message: String? = null, val statusCode: Int? = null
) {
    class Success<T>(data: T?, message: String? = null, statusCode: Int? = null) :
        Resource<T>(data = data, message = message, statusCode = statusCode)

    class Error<T>(message: String?, statusCode: Int? = null) :
        Resource<T>(message = message, statusCode = statusCode)

    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}