package com.cwramirezg.presentation.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Loaded<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}