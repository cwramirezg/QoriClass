package com.cwramirezg.core.domain.model

data class Auth(
    val id: String,
    val userName: String,
    val role: String,
    val template: String,
)
