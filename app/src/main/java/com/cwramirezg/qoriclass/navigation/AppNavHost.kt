package com.cwramirezg.qoriclass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.cwramirezg.authentication.navigation.AuthenticationNavGraph
import com.cwramirezg.authentication.navigation.authenticationNavGraph
import com.cwramirezg.home.navigation.HomeNavGraph
import com.cwramirezg.home.navigation.homeNavGraph

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AuthenticationNavGraph
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