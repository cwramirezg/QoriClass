package com.cwramirezg.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.cwramirezg.authentication.presentation.ui.screens.LoginScreen
import com.cwramirezg.authentication.presentation.ui.screens.RegisterScreen
import com.cwramirezg.core.navigation.AuthenticationNavGraph
import com.cwramirezg.core.navigation.Login
import com.cwramirezg.core.navigation.Register

fun NavGraphBuilder.authenticationNavGraph(
    navController: NavController,
    onNavHome: () -> Unit
) {
    navigation<AuthenticationNavGraph>(
        startDestination = Login
    ) {
        composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    onNavHome()
                },
                onNavigateToRegister = {
                    navController.navigate(Register)
                }
            )
        }

        composable<Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    onNavHome()
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}