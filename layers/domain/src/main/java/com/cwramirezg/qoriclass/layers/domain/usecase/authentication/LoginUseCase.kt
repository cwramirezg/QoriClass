package com.cwramirezg.qoriclass.layers.domain.usecase.authentication

import com.cwramirezg.qoriclass.layers.domain.model.Auth
import com.cwramirezg.qoriclass.layers.domain.model.Login
import com.cwramirezg.qoriclass.layers.domain.repository.AuthenticationRepository
import com.cwramirezg.qoriclass.layers.domain.result.DomainResult
import com.cwramirezg.qoriclass.layers.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<Login, Auth>() {
    override fun execute(parameter: Login): Flow<DomainResult<Auth>> {
        return repository.login(parameter)
    }
}
