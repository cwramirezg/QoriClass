package com.cwramirezg.authentication.presentation.utils

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import timber.log.Timber

suspend fun signInWithGoogle(
    credentialManager: CredentialManager,
    context: Context,
    onLoginWithGoogle: (String) -> Unit
) {
    val request = createGoogleSignInRequest()
    try {
        val result = credentialManager.getCredential(
            context = context,
            request = request,
        )
        handleSignInResult(
            result = result,
            onLoginWithGoogle = onLoginWithGoogle
        )
    } catch (e: GetCredentialException) {
        Timber.d("Error de acceso: ${e.message}")
    }
}

fun createGoogleSignInRequest(
    nonce: String? = null
): GetCredentialRequest {
    val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId("252794970771-gv8kb18gd9ib1ud2oenmjhv79uik6fpu.apps.googleusercontent.com")
            .setAutoSelectEnabled(true)
            .setNonce(nonce)
            .build()

    return GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
}

fun handleSignInResult(
    result: GetCredentialResponse,
    onLoginWithGoogle: (String) -> Unit
) {
    val credential = result.credential
    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        try {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            Timber.d("Inicio de sesión exitoso. ID Token: $idToken")
            onLoginWithGoogle(idToken)

        } catch (e: GoogleIdTokenParsingException) {
            Timber.d("Error al parsear el token de Google: ${e.message}")
        }
    } else {
        Timber.d("Credencial recibida de un tipo inesperado: ${credential.type}")
    }
}