package com.cwramirezg.authentication.di

import com.cwramirezg.qoriclass.layers.data.repository.AuthenticationRepositoryImpl
import com.cwramirezg.qoriclass.layers.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {

    @Binds
    @Singleton
    abstract fun provideAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WebClientId