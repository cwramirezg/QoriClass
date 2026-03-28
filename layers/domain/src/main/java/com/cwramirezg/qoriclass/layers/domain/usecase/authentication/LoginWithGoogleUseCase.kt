package com.cwramirezg.qoriclass.layers.domain.usecase.authentication

import com.cwramirezg.qoriclass.layers.domain.model.Auth
import com.cwramirezg.qoriclass.layers.domain.repository.AuthenticationRepository
import com.cwramirezg.qoriclass.layers.domain.result.DomainResult
import com.cwramirezg.qoriclass.layers.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) : BaseUseCase<String, Auth>() {
    override fun execute(parameter: String): Flow<DomainResult<Auth>> {
        return repository.googleSignIn(parameter)
    }
}
