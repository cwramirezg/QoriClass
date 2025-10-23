package com.cwramirezg.authentication.presentation.pojos

data class LoginState(
    val email: String = "",
    val password: String = "",
    val success: Boolean = false,
)
