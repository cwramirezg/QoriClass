package com.cwramirezg.qoriclass.layers.domain.repository

import com.cwramirezg.qoriclass.layers.domain.model.Auth
import com.cwramirezg.qoriclass.layers.domain.model.Login
import com.cwramirezg.qoriclass.layers.domain.model.Register
import com.cwramirezg.qoriclass.layers.domain.result.DomainResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun login(login: Login): Flow<DomainResult<Auth>>
    fun register(register: Register): Flow<DomainResult<Auth>>
    fun googleSignIn(idToken: String): Flow<DomainResult<Auth>>
    fun isUserLoggedIn(): Boolean
}