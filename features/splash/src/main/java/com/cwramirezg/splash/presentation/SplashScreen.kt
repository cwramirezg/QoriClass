package com.cwramirezg.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.cwramirezg.design.R as design

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            is SplashState.NavigateToHome -> onNavigateToHome()
            is SplashState.NavigateToLogin -> onNavigateToLogin()
            else -> Unit
        }
    }

    SplashContent(
        state = state
    )
}

@Composable
fun SplashContent(
    state: SplashState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = design.color.splash_bg)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = design.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.size(288.dp)
        )
        when (state) {
            is SplashState.ShowErrorDialog -> {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("Error") },
                    text = { Text(state.message) },
                    confirmButton = {
                        TextButton(onClick = {  }) {
                            Text("Reintentar")
                        }
                    }
                )
            }

            is SplashState.ShowUpdateDialog -> {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("Actualización disponible") },
                    text = { Text("Es necesario actualizar la aplicación para continuar.") },
                    confirmButton = {
                        TextButton(onClick = { }) {
                            Text("Actualizar")
                        }
                    }
                )
            }

            else -> Unit
        }
    }
}

@Preview
@Composable
private fun SplashContentPreview() {
    SplashContent(
        state = SplashState.Loading
    )
}
