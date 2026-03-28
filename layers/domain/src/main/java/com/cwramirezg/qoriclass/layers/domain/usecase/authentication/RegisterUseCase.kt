package com.cwramirezg.qoriclass.layers.domain.usecase.authentication

import com.cwramirezg.qoriclass.layers.domain.model.Auth
import com.cwramirezg.qoriclass.layers.domain.model.Register
import com.cwramirezg.qoriclass.layers.domain.repository.AuthenticationRepository
import com.cwramirezg.qoriclass.layers.domain.result.DomainResult
import com.cwramirezg.qoriclass.layers.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<Register, Auth>() {
    override fun execute(parameter: Register): Flow<DomainResult<Auth>> {
        return repository.register(parameter)
    }
}
