package ltd.bokadev.sparky_social_media.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ltd.bokadev.sparky_social_media.core.utils.signWithToken
import ltd.bokadev.sparky_social_media.data.repository.DataStoreImpl
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationInterceptor @Inject constructor(
    private val dataStoreImpl: DataStoreImpl
) : Interceptor {
    private var token = String()

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            chain.request().headers
            /** We can use runBlocking to run a suspend function from a normal function.
             * OkHttp interceptors run on a thread pool and not the main thread,
             * so it should be safe to obtain the current token there.
             *  */
            token = runBlocking { dataStoreImpl.getToken().first() }
            Timber.d("Token: $token")
            val signedRequest = chain.request() signWithToken token
            return chain.proceed(signedRequest)
        }
    }

    fun onTokenChanged(newToken: String) {
        runBlocking {
            dataStoreImpl.saveToken(newToken)
        }
    }

    fun isTokenSet() = token.isNotEmpty()

    fun clearSession() {
        runBlocking { dataStoreImpl.clearDatastore() }
    }
}