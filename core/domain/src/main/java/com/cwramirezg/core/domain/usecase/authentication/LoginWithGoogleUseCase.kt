package com.cwramirezg.core.domain.usecase.authentication

import com.cwramirezg.core.domain.model.Auth
import com.cwramirezg.core.domain.repository.AuthenticationRepository
import com.cwramirezg.core.domain.result.DomainResult
import com.cwramirezg.core.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<String, Auth>() {
    override fun execute(parameter: String): Flow<DomainResult<Auth>> {
        return repository.googleSignIn(parameter)
    }
}
