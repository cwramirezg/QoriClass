package com.cwramirezg.qoriclass.layers.domain.model

data class User(
    val id: String,
    val userName: String,
    val role: String,
    val template: String,
    val language: String,
)
