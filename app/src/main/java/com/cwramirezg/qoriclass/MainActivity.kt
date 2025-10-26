package com.cwramirezg.qoriclass

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.cwramirezg.core.navigation.AuthenticationNavGraph
import com.cwramirezg.qoriclass.navigation.AppNavHost
import com.cwramirezg.qoriclass.ui.theme.QoriClassTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QoriClassTheme {
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    navController.currentBackStack.collectLatest {
                        Timber.d(
                            "currentBackStack: ${
                                it.map { entry ->
                                    entry.destination.route?.split(".")?.last()
                                }
                            }"
                        )
                    }
                }
                AppNavHost(
                    navController = navController,
                    startDestination = getStartDestination()
                )
            }
        }
    }
}

private fun getStartDestination(): Any {
    return AuthenticationNavGraph
}
