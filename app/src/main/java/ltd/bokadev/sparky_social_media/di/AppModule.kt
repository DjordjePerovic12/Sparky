package ltd.bokadev.sparky_social_media.di

import com.boguszpawlowski.composecalendar.BuildConfig
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ltd.bokadev.sparky_social_media.data.AuthorizationInterceptor
import ltd.bokadev.sparky_social_media.data.remote.dto.ApiErrorDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: AuthorizationInterceptor
    ): OkHttpClient {
        val okHttpClient =
            OkHttpClient.Builder().retryOnConnectionFailure(false).addInterceptor(interceptor)
        if (BuildConfig.DEBUG) okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        Timber.e("Created okhttp instance $okHttpClient")

        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideErrorAdapter(): JsonAdapter<ApiErrorDto> {
        Timber.e("Created adapter")
        return Moshi.Builder().build().adapter(ApiErrorDto::class.java).lenient()
    }
}