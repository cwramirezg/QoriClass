package com.cwramirezg.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cwramirezg.core.navigation.Home
import com.cwramirezg.core.navigation.HomeNavGraph
import com.cwramirezg.home.presentation.ui.screens.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    navigation<HomeNavGraph>(
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen()
        }
    }
}