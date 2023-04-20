package com.example.cityfootie_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cityfootie_compose.screens.CreateAccountScreen
import com.example.cityfootie_compose.screens.LoginScreen
import com.example.cityfootie_compose.screens.MainScreen
import com.example.cityfootie_compose.screens.SplashScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
        composable(route = AppScreens.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(route = AppScreens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.CreateAccountScreen.route) {
            CreateAccountScreen(navController)
        }
    }
}