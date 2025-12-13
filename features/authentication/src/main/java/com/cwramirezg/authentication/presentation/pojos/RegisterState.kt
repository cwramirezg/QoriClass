package com.cwramirezg.authentication.presentation.pojos

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val success: Boolean = false,
)
