package com.cwramirezg.authentication.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cwramirezg.authentication.presentation.pojos.RegisterEvent
import com.cwramirezg.authentication.presentation.pojos.RegisterState
import com.cwramirezg.authentication.presentation.viewmodel.RegisterViewModel
import com.cwramirezg.core.presentation.base.BaseScreen
import com.cwramirezg.core.presentation.components.DefaultErrorContent
import timber.log.Timber

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BaseScreen(
        uiState = uiState,
        errorContent = { message, retry ->
            DefaultErrorContent(
                message = message,
                onRetry = {
                    viewModel.onEvent(RegisterEvent.OnRetry)
                }
            )
        }
    ) { state ->
        LaunchedEffect(state.success) {
            Timber.d("Success: ${state.success}")
            if (state.success) {
                onRegisterSuccess()
            }
        }

        RegisterContent(
            state = state,
            onEvent = viewModel::onEvent,
            onNavigateToLogin = onNavigateToLogin
        )
    }

}

@Composable
fun RegisterContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.UpdateEmail(it)) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.UpdatePassword(it)) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = { onEvent(RegisterEvent.OnRegister) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            TextButton(onClick = onNavigateToLogin) {
                Text("¿Ya tienes cuenta? Inicia Sesión")
            }
        }
    }
}
