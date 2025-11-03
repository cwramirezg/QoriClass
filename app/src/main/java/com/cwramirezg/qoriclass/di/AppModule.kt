package com.cwramirezg.qoriclass.di

import android.content.Context
import com.cwramirezg.authentication.di.WebClientId
import com.cwramirezg.qoriclass.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @WebClientId
    fun provideWebClientId(@ApplicationContext context: Context): String {
        return context.getString(R.string.default_web_client_id)
    }
}