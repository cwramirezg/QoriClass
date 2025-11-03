package com.cwramirezg.authentication.presentation.utils

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.delay
import timber.log.Timber
import java.security.SecureRandom
import java.util.Base64

suspend fun signInWithGoogle(
    credentialManager: CredentialManager,
    context: Context,
    webClientId: String,
    filterByAuthorizedAccounts: Boolean,
    onLoginWithGoogle: (String) -> Unit
) {
    if (filterByAuthorizedAccounts) {
        val request = createGoogleSignInRequest(webClientId, true)
        delay(250)
        val e = signIn(credentialManager, request, context, onLoginWithGoogle)
        if (e is NoCredentialException) {
            val requestFalse = createGoogleSignInRequest(webClientId, false)
            signIn(credentialManager, requestFalse, context, onLoginWithGoogle)
        }
    } else {
        val request = createGoogleSignInRequest(webClientId)
        signIn(credentialManager, request, context, onLoginWithGoogle)
    }
}

fun createGoogleSignInRequest(
    webClientId: String,
    filterByAuthorizedAccounts: Boolean
): GetCredentialRequest {
    val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
            .setServerClientId(webClientId)
            // .setAutoSelectEnabled(true)
            .setNonce(generateSecureRandomNonce())
            .build()

    return GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
}

fun createGoogleSignInRequest(
    webClientId: String,
): GetCredentialRequest {
    val googleIdOption: GetGoogleIdOption =
        GetGoogleIdOption.Builder()
            .setServerClientId(webClientId)
            // .setAutoSelectEnabled(true)
            .setNonce(generateSecureRandomNonce())
            .build()

    return GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()
}

suspend fun signIn(
    credentialManager: CredentialManager,
    request: GetCredentialRequest,
    context: Context,
    onLoginWithGoogle: (String) -> Unit
): Exception? {
    val e: Exception? = null
    delay(250)
    try {
        val result = credentialManager.getCredential(
            context = context,
            request = request,
        )
        val credential = result.credential
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            Timber.d("Inicio de sesión exitoso. ID Token: $idToken")
            onLoginWithGoogle(idToken)
        } else {
            Timber.d("Credencial recibida de un tipo inesperado: ${credential.type}")
        }
    } catch (e: GetCredentialException) {
        Timber.d("Sign in failed!: Failure getting credentials")
    } catch (e: GoogleIdTokenParsingException) {
        Timber.d("Sign in failed! Issue with parsing received GoogleIdToken")
    } catch (e: NoCredentialException) {
        Timber.d("Sign in failed! No credentials found")
        return e
    } catch (e: GetCredentialCustomException) {
        Timber.d("Sign in failed! Issue with custom credential request")
    } catch (e: GetCredentialCancellationException) {
        Timber.d("Sign in failed! Sign-in was cancelled")
    }
    return e
}

fun generateSecureRandomNonce(byteLength: Int = 32): String {
    val randomBytes = ByteArray(byteLength)
    SecureRandom.getInstanceStrong().nextBytes(randomBytes)
    return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
}