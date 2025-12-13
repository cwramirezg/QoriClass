package com.cwramirezg.core.domain.repository

import com.cwramirezg.core.domain.model.Auth
import com.cwramirezg.core.domain.model.Login
import com.cwramirezg.core.domain.model.Register
import com.cwramirezg.core.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun login(login: Login): Flow<DomainResult<Auth>>
    fun register(register: Register): Flow<DomainResult<Auth>>
    fun googleSignIn(idToken: String): Flow<DomainResult<Auth>>
}