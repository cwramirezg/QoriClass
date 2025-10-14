package com.cwramirezg.core.domain.usecase

import com.cwramirezg.core.domain.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseUseCase<in P, R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(parameter: P): Flow<DomainResult<R>> =
        execute(parameter).flowOn(coroutineDispatcher)

    protected abstract suspend fun execute(parameter: P): Flow<DomainResult<R>>
}

abstract class BaseUseCaseNoParams<R>(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<DomainResult<R>> =
        execute().flowOn(coroutineDispatcher)

    protected abstract suspend fun execute(): Flow<DomainResult<R>>
}
