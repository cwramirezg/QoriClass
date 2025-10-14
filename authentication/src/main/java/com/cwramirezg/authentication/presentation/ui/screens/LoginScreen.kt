package com.cwramirezg.authentication.presentation.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cwramirezg.authentication.R
import com.cwramirezg.authentication.presentation.pojos.LoginEvent
import com.cwramirezg.authentication.presentation.pojos.LoginState
import com.cwramirezg.authentication.presentation.viewmodel.LoginViewModel
import com.cwramirezg.core.presentation.base.BaseScreen
import com.cwramirezg.core.presentation.components.DefaultErrorContent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import timber.log.Timber

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { idToken ->
                    viewModel.onEvent(LoginEvent.LoginWithGoogle(idToken))
                }
            } catch (e: ApiException) {
                Timber.e(e, "Error al iniciar sesión con Google")
            }
        }
    )

    BaseScreen(
        uiState = uiState, errorContent = { message, retry ->
            DefaultErrorContent(
                message = message, onRetry = {
                    viewModel.onEvent(LoginEvent.OnRetry)
                })
        }) { state ->
        LaunchedEffect(state.success) {
            Timber.d("Success: ${state.success}")
            if (state.success) {
                onLoginSuccess()
            }
        }
        LoginContent(
            state = state,
            onEvent = { viewModel.onEvent(it) },
            onNavigateToRegister = onNavigateToRegister,
            onLauncher = {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("252794970771-gv8kb18gd9ib1ud2oenmjhv79uik6fpu.apps.googleusercontent.com")
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                launcher.launch(googleSignInClient.signInIntent)
            }
        )
    }
}

@Composable
fun LoginContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onNavigateToRegister: () -> Unit,
    onLauncher: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.UpdateEmail(it)) },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.UpdatePassword(it)) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onEvent.invoke(LoginEvent.OnLogin) }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { onLauncher() }, modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Iniciar sesión con Google")
            }
            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
