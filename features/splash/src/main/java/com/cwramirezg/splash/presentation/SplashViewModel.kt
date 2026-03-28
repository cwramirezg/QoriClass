package com.cwramirezg.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwramirezg.qoriclass.layers.domain.usecase.app.AppUpdateStatus
import com.cwramirezg.qoriclass.layers.domain.usecase.app.CheckAppUpdateUseCase
import com.cwramirezg.qoriclass.layers.domain.usecase.authentication.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAppUpdateUseCase: CheckAppUpdateUseCase,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state = _state.asStateFlow()

    init {
        initializeApp()
    }

    private fun initializeApp() {
        viewModelScope.launch {
            try {
                // 1. Verificar actualización
                val updateStatus = checkAppUpdateUseCase()
                if (updateStatus is AppUpdateStatus.MandatoryUpdate) {
                    _state.value = SplashState.ShowUpdateDialog
                    return@launch
                }

                // 2. Simular consulta de datos globales o API
                delay(1500)

                // 3. Verificar sesión
                if (isUserLoggedInUseCase()) {
                    _state.value = SplashState.NavigateToHome
                } else {
                    _state.value = SplashState.NavigateToLogin
                }

            } catch (e: Exception) {
                _state.value = SplashState.ShowErrorDialog(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class SplashState {
    object Loading : SplashState()
    object NavigateToHome : SplashState()
    object NavigateToLogin : SplashState()
    object ShowUpdateDialog : SplashState()
    data class ShowErrorDialog(val message: String) : SplashState()
}
