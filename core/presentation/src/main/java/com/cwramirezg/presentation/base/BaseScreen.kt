package com.cwramirezg.presentation.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cwramirezg.presentation.components.DefaultErrorContent
import com.cwramirezg.presentation.components.DefaultLoadingContent
import com.cwramirezg.presentation.state.UiState

@Composable
fun <T> BaseScreen(
    uiState: UiState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    containerAlignment: Alignment = Alignment.Center,
    loadingContent: @Composable () -> Unit = { DefaultLoadingContent() },
    errorContent: @Composable (String, (() -> Unit)?) -> Unit = { message, retry ->
        DefaultErrorContent(message, retry)
    },
    loadedContent: @Composable (T) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = containerAlignment
    ) {
        when (uiState) {
            is UiState.Loading -> loadingContent()
            is UiState.Loaded -> {
                uiState.data?.let { data ->
                    loadedContent(data)
                } ?: run {
                    errorContent("No hay datos disponibles", onRetry)
                }
            }

            is UiState.Error -> errorContent(uiState.message, onRetry)
        }
    }
}
