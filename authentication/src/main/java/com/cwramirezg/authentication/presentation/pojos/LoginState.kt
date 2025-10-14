package com.cwramirezg.authentication.presentation.pojos

data class LoginState(
    val email: String = "cwramirezg@gmail.com",
    val password: String = "",
    val success: Boolean = false,
)
