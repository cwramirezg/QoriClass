package com.cwramirezg.core.domain.usecase.authentication

import com.cwramirezg.core.domain.model.Auth
import com.cwramirezg.core.domain.model.Register
import com.cwramirezg.core.domain.repository.AuthenticationRepository
import com.cwramirezg.core.domain.result.DomainResult
import com.cwramirezg.core.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<Register, Auth>() {
    override fun execute(parameter: Register): Flow<DomainResult<Auth>> {
        return repository.register(parameter)
    }
}
