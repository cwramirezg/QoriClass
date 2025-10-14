package com.cwramirezg.authentication.data.remote.gms.datasource

import com.cwramirezg.core.data.repository.RepositoryResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationGmsDatasource @Inject constructor(
    private val auth: FirebaseAuth,
) {

    fun login(email: String, password: String): Flow<RepositoryResult<AuthResult>> =
        flow {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                emit(RepositoryResult.Success(result))
            } catch (e: Exception) {
                emit(RepositoryResult.Error(e.message ?: "Ocurrió un error", e))
            }
        }

    fun register(email: String, password: String): Flow<RepositoryResult<AuthResult>> = flow {
        try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            emit(RepositoryResult.Success(result))
        } catch (e: Exception) {
            emit(RepositoryResult.Error(e.message ?: "Ocurrió un error", e))
        }
    }

    fun googleSignIn(idToken: String): Flow<RepositoryResult<AuthResult>> = flow {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            emit(RepositoryResult.Success(result))
        } catch (e: Exception) {
            emit(RepositoryResult.Error(e.message ?: "Ocurrió un error", e))
        }
    }
}