package com.cwramirezg.qoriclass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cwramirezg.authentication.navigation.authenticationNavGraph
import com.cwramirezg.core.navigation.AuthenticationNavGraph
import com.cwramirezg.core.navigation.HomeNavGraph
import com.cwramirezg.home.navigation.homeNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
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