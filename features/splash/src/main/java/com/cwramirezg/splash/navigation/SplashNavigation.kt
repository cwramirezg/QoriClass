package com.cwramirezg.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cwramirezg.splash.presentation.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashNavGraph

fun NavGraphBuilder.splashNavGraph(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable<SplashNavGraph> {
        SplashScreen(
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToHome = onNavigateToHome
        )
    }
}
