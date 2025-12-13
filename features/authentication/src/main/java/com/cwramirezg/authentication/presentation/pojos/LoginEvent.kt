package com.cwramirezg.authentication.presentation.pojos

sealed interface LoginEvent {
    data class UpdateEmail(val email: String) : LoginEvent
    data class UpdatePassword(val password: String) : LoginEvent
    data class LoginWithGoogle(val idToken: String) : LoginEvent
    object OnLogin : LoginEvent
    object OnRetry : LoginEvent
}