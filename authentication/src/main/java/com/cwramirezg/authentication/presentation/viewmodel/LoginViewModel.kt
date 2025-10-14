package com.cwramirezg.authentication.presentation.viewmodel

import com.cwramirezg.authentication.domain.model.Login
import com.cwramirezg.authentication.domain.usecase.LoginUseCase
import com.cwramirezg.authentication.domain.usecase.LoginWithGoogleUseCase
import com.cwramirezg.authentication.presentation.pojos.LoginEvent
import com.cwramirezg.authentication.presentation.pojos.LoginState
import com.cwramirezg.core.domain.result.DomainResult
import com.cwramirezg.core.presentation.base.BaseViewModel
import com.cwramirezg.core.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase
) : BaseViewModel() {

    private var state = LoginState()

    private val _uiState = MutableStateFlow<UiState<LoginState>>(UiState.Loaded(state))
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLogin -> {
                launchSafe {
                    _uiState.update { UiState.Loading }
                    val login = Login(state.email, state.password)
                    loginUseCase(login).collect { result ->
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

            is LoginEvent.LoginWithGoogle -> {
                launchSafe {
                    _uiState.update { UiState.Loading }
                    loginWithGoogleUseCase(event.idToken).collect { result ->
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

            LoginEvent.OnRetry -> {
                _uiState.update { UiState.Loaded(state) }
            }

            is LoginEvent.UpdateEmail -> {
                state = state.copy(email = event.email)
                _uiState.update { UiState.Loaded(state) }
            }

            is LoginEvent.UpdatePassword -> {
                state = state.copy(password = event.password)
                _uiState.update { UiState.Loaded(state) }
            }
        }
    }

    override fun onError(message: String) {
        _uiState.update { UiState.Error(message) }
    }
}