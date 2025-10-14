package com.cwramirezg.authentication.domain.usecase

import com.cwramirezg.authentication.domain.model.Login
import com.cwramirezg.authentication.domain.repository.AuthenticationRepository
import com.cwramirezg.core.data.repository.RepositoryResult
import com.cwramirezg.core.domain.result.DomainResult
import com.cwramirezg.core.domain.usecase.BaseUseCase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<Login, AuthResult>() {
    override suspend fun execute(parameter: Login): Flow<DomainResult<AuthResult>> {
        return repository.login(parameter).map { result ->
            when (result) {
                is RepositoryResult.Success -> {
                    DomainResult.Success(result.data)
                }

                is RepositoryResult.Error -> {
                    DomainResult.Error(result.message, result.exception)
                }
            }
        }
    }

}