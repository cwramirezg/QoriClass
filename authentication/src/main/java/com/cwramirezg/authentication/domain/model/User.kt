package com.cwramirezg.authentication.domain.model

data class User(
    val id: String,
    val userName: String,
    val role: String,
    val template: String,
    val language: String,
)
