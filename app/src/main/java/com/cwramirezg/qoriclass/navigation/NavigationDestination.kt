package com.cwramirezg.qoriclass.navigation

import kotlinx.serialization.Serializable

sealed class NavigationDestination {
    @Serializable
    object Login : NavigationDestination()

    @Serializable
    object Register : NavigationDestination()

    @Serializable
    object Home : NavigationDestination()
}
