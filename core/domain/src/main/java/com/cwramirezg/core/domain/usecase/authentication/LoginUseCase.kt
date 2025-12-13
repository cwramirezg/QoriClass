package com.cwramirezg.core.domain.usecase.authentication

import com.cwramirezg.core.domain.model.Auth
import com.cwramirezg.core.domain.model.Login
import com.cwramirezg.core.domain.repository.AuthenticationRepository
import com.cwramirezg.core.domain.result.DomainResult
import com.cwramirezg.core.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<Login, Auth>() {
    override fun execute(parameter: Login): Flow<DomainResult<Auth>> {
        return repository.login(parameter)
    }
}
