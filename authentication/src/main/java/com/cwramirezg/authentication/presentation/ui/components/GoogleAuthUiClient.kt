package com.cwramirezg.authentication.presentation.ui.components

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.cwramirezg.authentication.R
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GoogleAuthUiClient @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val webClientId: String = context.getString(R.string.default_web_client_id)

    suspend fun getSignInIntentSender(): IntentSender? {
        return try {
            val result = oneTapClient.getSignInIntent(
                GetSignInIntentRequest.builder()
                    .setServerClientId(webClientId)
                    .build()
            ).await()
            result.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    suspend fun getSignInResultFromIntent(intent: Intent): String? {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        return credential.googleIdToken
    }
}
