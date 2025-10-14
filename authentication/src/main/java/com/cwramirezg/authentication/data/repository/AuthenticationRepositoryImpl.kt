package com.cwramirezg.authentication.data.repository


import com.cwramirezg.authentication.data.remote.gms.datasource.AuthenticationGmsDatasource
import com.cwramirezg.authentication.domain.model.Login
import com.cwramirezg.authentication.domain.model.Register
import com.cwramirezg.authentication.domain.repository.AuthenticationRepository
import com.cwramirezg.core.data.repository.RepositoryResult
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationGmsDatasource: AuthenticationGmsDatasource
) : AuthenticationRepository {
    override suspend fun login(login: Login): Flow<RepositoryResult<AuthResult>> {
        return authenticationGmsDatasource.login(login.email, login.password)
    }

    override suspend fun register(register: Register): Flow<RepositoryResult<AuthResult>> {
        return authenticationGmsDatasource.register(register.email, register.password)
    }

    override suspend fun googleSignIn(idToken: String): Flow<RepositoryResult<AuthResult>> {
        return authenticationGmsDatasource.googleSignIn(idToken)
    }

}