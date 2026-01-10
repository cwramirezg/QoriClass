package com.cwramirezg.qoriclass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cwramirezg.authentication.navigation.AuthenticationNavGraph
import com.cwramirezg.authentication.navigation.authenticationNavGraph
import com.cwramirezg.home.navigation.HomeNavGraph
import com.cwramirezg.home.navigation.homeNavGraph
import com.cwramirezg.splash.navigation.SplashNavGraph
import com.cwramirezg.splash.navigation.splashNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SplashNavGraph
    ) {
        splashNavGraph(
            onNavigateToLogin = {
                navController.navigate(AuthenticationNavGraph) {
                    popUpTo(SplashNavGraph) {
                        inclusive = true
                    }
                }
            },
            onNavigateToHome = {
                navController.navigate(HomeNavGraph) {
                    popUpTo(SplashNavGraph) {
                        inclusive = true
                    }
                }
            }
        )
        authenticationNavGraph(
            navController = navController,
            onNavHome = {
                navController.navigate(HomeNavGraph) {
                    popUpTo(AuthenticationNavGraph) {
                        inclusive = true
                    }
                }
            }
        )
        homeNavGraph(
            navController = navController
        )
    }
}