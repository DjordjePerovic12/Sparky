package ltd.bokadev.sparky_social_media.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ltd.bokadev.sparky_social_media.core.navigation.Navigator
import ltd.bokadev.sparky_social_media.core.navigation.NavigatorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractionModule {
    @Binds
    @Singleton
    abstract fun bindNavigator(
        navigatorImpl: NavigatorImpl
    ): Navigator
}