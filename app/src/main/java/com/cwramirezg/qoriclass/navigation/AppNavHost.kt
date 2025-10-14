package com.cwramirezg.qoriclass.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cwramirezg.authentication.presentation.ui.screens.LoginScreen
import com.cwramirezg.authentication.presentation.ui.screens.RegisterScreen
import timber.log.Timber

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavigationDestination.Login> {
            Timber.d("Renderizando LoginScreen")
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavigationDestination.Home) {
                        popUpTo(NavigationDestination.Login) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavigationDestination.Register)
                }
            )
        }

        composable<NavigationDestination.Register> {
            Timber.d("Renderizando RegisterScreen")
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(NavigationDestination.Home) {
                        popUpTo(NavigationDestination.Register) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable<NavigationDestination.Home> {
            Timber.d("Renderizando HomeScreen")
            Text(text = "Home")
        }
    }
}