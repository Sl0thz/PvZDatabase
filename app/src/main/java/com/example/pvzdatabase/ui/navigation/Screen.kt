package com.example.pvzdatabase.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{plantId}") {
        fun createRoute(plantId: Int) = "home/$plantId"
    }
}