package com.cwramirezg.core.domain.model

data class IniciarSesion(
    val username: String,
    val password: String,
    val language: String,
    val deviceId: String,
    val deviceName: String,
    val deviceVersion: String,
    val appVersion: String,
    val platform: String,
)
