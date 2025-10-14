package com.cwramirezg.authentication.di

import android.content.Context
import com.cwramirezg.authentication.presentation.ui.components.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesOneTapClient(@ApplicationContext context: Context): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    @Singleton
    fun providesGoogleAuthUiClient(
        @ApplicationContext context: Context,
        oneTapClient: SignInClient,
    ): GoogleAuthUiClient {
        return GoogleAuthUiClient(context, oneTapClient)
    }
}
