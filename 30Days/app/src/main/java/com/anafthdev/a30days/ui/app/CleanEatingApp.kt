package com.anafthdev.a30days.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anafthdev.a30days.data.CleanEatingScreen
import com.anafthdev.a30days.ui.home.HomeScreen

@Composable
fun CleanEatingApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CleanEatingScreen.Home.name
    ) {
        composable(CleanEatingScreen.Home.name) {
            HomeScreen()
        }
    }
}
