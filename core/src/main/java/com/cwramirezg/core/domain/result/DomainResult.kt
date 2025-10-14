package com.cwramirezg.core.domain.result

sealed class DomainResult<T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error<T>(
        val message: String,
        val exception: Throwable? = null
    ) : DomainResult<T>()
}
