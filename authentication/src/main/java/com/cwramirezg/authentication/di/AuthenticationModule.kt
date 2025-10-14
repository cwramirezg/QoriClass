package com.cwramirezg.authentication.di

import com.cwramirezg.authentication.data.repository.AuthenticationRepositoryImpl
import com.cwramirezg.authentication.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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