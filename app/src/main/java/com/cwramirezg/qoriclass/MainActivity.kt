package com.cwramirezg.qoriclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.cwramirezg.qoriclass.navigation.AppNavHost
import com.cwramirezg.qoriclass.navigation.NavigationDestination
import com.cwramirezg.qoriclass.ui.theme.QoriClassTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QoriClassTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    startDestination = getStartDestination()
                )
            }
        }
    }
}

private fun getStartDestination(): Any {
    return NavigationDestination.Login
}
