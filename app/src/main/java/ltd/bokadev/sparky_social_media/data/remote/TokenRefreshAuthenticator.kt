package ltd.bokadev.sparky_social_media.data.remote

import android.content.Context
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ltd.bokadev.sparky_social_media.core.utils.signWithToken
import ltd.bokadev.sparky_social_media.data.AuthorizationInterceptor
import ltd.bokadev.sparky_social_media.data.remote.dto.AccessTokenRequestDto
import ltd.bokadev.sparky_social_media.data.remote.services.SparkyService
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRefreshAuthenticator @Inject constructor(
    private val interceptor: AuthorizationInterceptor,
    private val dataStoreRepository: DataStoreRepository,
    @ApplicationContext private val context: Context
) : Authenticator {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    internal interface TokenRefreshAuthenticatorEntryPoint {
        fun sparkyService(): SparkyService
    }

    private var sparkyService: SparkyService? = null
    private var isRefreshInProgress = false

    private fun initSecurityService() {
        val appContext: Context = context.applicationContext
        val entryPoint = EntryPointAccessors.fromApplication(
            appContext, TokenRefreshAuthenticatorEntryPoint::class.java
        )
        sparkyService = entryPoint.sparkyService()
    }

    private val Response.retryCount: Int
        get() {
            var result = 1
            var currentResponse = priorResponse
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse
            }
            return result
        }

    override fun authenticate(route: Route?, response: Response): Request? =
        if (interceptor.isTokenSet() && response.retryCount < 2 && response.request.url.toString() != sparkyService?.accessToken(
                getUserToken()
            )
                ?.request()?.url?.toString() && !isRefreshInProgress
        ) response.createSignedRequest()
        else {
            interceptor.clearSession()
            null
        }

    private fun Response.createSignedRequest(): Request? = try {
        runBlocking {
            isRefreshInProgress = true
            if (sparkyService == null) initSecurityService()
            val response = sparkyService?.accessToken(getUserToken())?.execute()
            val token = response?.body()?.accessToken
            token?.let { interceptor.onTokenChanged(it) }
            isRefreshInProgress = false
            request signWithToken token
        }
    } catch (e: Throwable) {
        Timber.e(e, "Failed to resign request")
        null
    }

    private fun getUserToken(): AccessTokenRequestDto {
        val userId = runBlocking { dataStoreRepository.getUser().first().id }
        val refreshToken = runBlocking { dataStoreRepository.getUser().first().refreshToken }
        return AccessTokenRequestDto(userId = userId, refreshToken = refreshToken)
    }
}
