package com.cwramirezg.qoriclass.layers.domain.usecase.authentication

import com.cwramirezg.qoriclass.layers.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}
