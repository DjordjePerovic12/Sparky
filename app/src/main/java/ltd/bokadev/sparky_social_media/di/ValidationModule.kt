package ltd.bokadev.sparky_social_media.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ltd.bokadev.sparky_social_media.core.validation.ValidateEmail
import ltd.bokadev.sparky_social_media.core.validation.ValidatePassword

@Module
@InstallIn(ViewModelComponent::class)
object ValidationModule {

    @ViewModelScoped
    @Provides
    fun provideEmailValidation(): ValidateEmail = ValidateEmail()

    @ViewModelScoped
    @Provides
    fun providePasswordValidation(): ValidatePassword = ValidatePassword()
}
