package com.cwramirezg.core.data.repository

sealed class RepositoryResult<T> {
    data class Success<T>(val data: T) : RepositoryResult<T>()
    data class Error<T>(
        val message: String,
        val exception: Throwable? = null
    ) : RepositoryResult<T>()
}
