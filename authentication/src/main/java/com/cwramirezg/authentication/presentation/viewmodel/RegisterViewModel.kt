package com.cwramirezg.authentication.presentation.viewmodel

import com.cwramirezg.authentication.domain.model.Register
import com.cwramirezg.authentication.domain.usecase.RegisterUseCase
import com.cwramirezg.authentication.presentation.pojos.RegisterEvent
import com.cwramirezg.authentication.presentation.pojos.RegisterState
import com.cwramirezg.core.domain.result.DomainResult
import com.cwramirezg.core.presentation.base.BaseViewModel
import com.cwramirezg.core.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel() {

    private var state = RegisterState()

    private val _uiState = MutableStateFlow<UiState<RegisterState>>(UiState.Loaded(state))
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnRegister -> {
                launchSafe {
                    _uiState.update { UiState.Loading }
                    val register = Register(state.email, state.password)
                    registerUseCase(register).collect { result ->
                        when (result) {
                            is DomainResult.Success -> {
                                _uiState.update {
                                    state = state.copy(success = true)
                                    UiState.Loaded(state)
                                }
                            }

                            is DomainResult.Error -> {
                                _uiState.update { UiState.Error(result.message) }
                            }
                        }
                    }
                }
            }

            RegisterEvent.OnRetry -> {
                _uiState.update { UiState.Loaded(state) }
            }

            is RegisterEvent.UpdateEmail -> {
                state = state.copy(email = event.email)
                _uiState.update { UiState.Loaded(state) }
            }

            is RegisterEvent.UpdatePassword -> {
                state = state.copy(password = event.password)
                _uiState.update { UiState.Loaded(state) }
            }
        }
    }

    override fun onError(message: String) {
        _uiState.update { UiState.Error(message) }
    }
}