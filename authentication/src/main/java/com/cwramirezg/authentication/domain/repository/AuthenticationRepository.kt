package com.cwramirezg.authentication.domain.repository

import com.cwramirezg.authentication.domain.model.Login
import com.cwramirezg.authentication.domain.model.Register
import com.cwramirezg.core.data.repository.RepositoryResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(login: Login): Flow<RepositoryResult<AuthResult>>
    suspend fun register(register: Register): Flow<RepositoryResult<AuthResult>>
    suspend fun googleSignIn(idToken: String): Flow<RepositoryResult<AuthResult>>
}