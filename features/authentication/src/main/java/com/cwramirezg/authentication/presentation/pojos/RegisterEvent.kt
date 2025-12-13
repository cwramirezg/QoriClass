package com.cwramirezg.authentication.presentation.pojos

sealed interface RegisterEvent {
    data class UpdateEmail(val email: String) : RegisterEvent
    data class UpdatePassword(val password: String) : RegisterEvent
    object OnRegister : RegisterEvent
    object OnRetry : RegisterEvent
}