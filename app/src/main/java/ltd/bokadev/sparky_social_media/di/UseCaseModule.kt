package ltd.bokadev.sparky_social_media.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ltd.bokadev.sparky_social_media.core.validation.ValidateEmail
import ltd.bokadev.sparky_social_media.core.validation.ValidatePassword
import ltd.bokadev.sparky_social_media.domain.repository.SparkyRepository
import ltd.bokadev.sparky_social_media.domain.use_case.EmailUseCase
import ltd.bokadev.sparky_social_media.domain.use_case.PasswordUseCase
import ltd.bokadev.sparky_social_media.domain.use_case.RegistrationUseCase


@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @ViewModelScoped
    @Provides
    fun provideRegistrationUseCase(
        repository: SparkyRepository
    ): RegistrationUseCase {
        return RegistrationUseCase(repository)
    }


    @ViewModelScoped
    @Provides
    fun provideEmailUseCase(
        validateEmail: ValidateEmail
    ): EmailUseCase {
        return EmailUseCase(validateEmail)
    }

    @ViewModelScoped
    @Provides
    fun providePasswordUseCase(
        validatePassword: ValidatePassword
    ): PasswordUseCase {
        return PasswordUseCase(validatePassword)
    }
}