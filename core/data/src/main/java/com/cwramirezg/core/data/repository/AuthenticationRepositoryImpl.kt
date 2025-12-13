package com.cwramirezg.core.data.repository

import com.cwramirezg.core.data.remote.gms.datasource.AuthenticationGmsDatasource
import com.cwramirezg.core.data.result.RepositoryResult
import com.cwramirezg.core.domain.model.Auth
import com.cwramirezg.core.domain.model.Login
import com.cwramirezg.core.domain.model.Register
import com.cwramirezg.core.domain.repository.AuthenticationRepository
import com.cwramirezg.core.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationGmsDatasource: AuthenticationGmsDatasource
) : AuthenticationRepository {
    override fun login(login: Login): Flow<DomainResult<Auth>> {
        return authenticationGmsDatasource.login(login.email, login.password).map { result ->
            when (result) {
                is RepositoryResult.Error -> {
                    DomainResult.Error(result.message, result.exception)
                }

                is RepositoryResult.Success -> {
                    val auth = Auth(
                        id = result.data.user?.uid ?: "",
                        userName = result.data.user?.displayName ?: "",
                        role = "",
                        template = "",
                    )
                    DomainResult.Success(auth)
                }
            }
        }
    }

    override fun register(register: Register): Flow<DomainResult<Auth>> {
        return authenticationGmsDatasource.register(register.email, register.password)
            .map { result ->
                when (result) {
                    is RepositoryResult.Error -> {
                        DomainResult.Error(result.message, result.exception)
                    }

                    is RepositoryResult.Success -> {
                        val auth = Auth(
                            id = result.data.user?.uid ?: "",
                            userName = result.data.user?.displayName ?: "",
                            role = "",
                            template = "",
                        )
                        DomainResult.Success(auth)
                    }
                }
            }
    }

    override fun googleSignIn(idToken: String): Flow<DomainResult<Auth>> {
        return authenticationGmsDatasource.googleSignIn(idToken).map { result ->
            when (result) {
                is RepositoryResult.Error -> {
                    DomainResult.Error(result.message, result.exception)
                }

                is RepositoryResult.Success -> {
                    val auth = Auth(
                        id = result.data.user?.uid ?: "",
                        userName = result.data.user?.displayName ?: "",
                        role = "",
                        template = "",
                    )
                    DomainResult.Success(auth)
                }
            }
        }
    }

}