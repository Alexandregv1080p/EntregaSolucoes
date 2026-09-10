package com.fasttrade.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fasttrade.android.ui.auth.LoginScreen
import com.fasttrade.android.ui.auth.RegisterScreen
import com.fasttrade.android.ui.home.HomeScreen
import com.fasttrade.android.ui.product.ProductScreen

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "login") {
        composable("login")    { LoginScreen(nav) }
        composable("register") { RegisterScreen(nav) }
        composable("home")     { HomeScreen(nav) }
        composable("product/{id}") { back ->
            ProductScreen(back.arguments?.getString("id")?.toLong() ?: 0L, nav)
        }
    }
}
