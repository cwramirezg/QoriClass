package com.cwramirezg.core.domain.usecase.authentication

import com.cwramirezg.core.domain.repository.AuthenticationRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(): Boolean {
        return repository.isUserLoggedIn()
    }
}
