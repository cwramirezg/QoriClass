package com.cwramirezg.core.presentation.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Loaded<T>(val data: T) : UiState<T>()
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : UiState<Nothing>()
}
