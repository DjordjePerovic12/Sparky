package ltd.bokadev.sparky_social_media.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ltd.bokadev.sparky_social_media.data.repository.DataStoreImpl
import ltd.bokadev.sparky_social_media.domain.repository.DataStoreRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreImpl: DataStoreImpl
    ): DataStoreRepository
}