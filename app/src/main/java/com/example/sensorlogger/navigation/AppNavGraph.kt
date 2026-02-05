package com.example.sensorlogger.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun AppNavGraph(
    navController: NavHostController,
    dashboard: @Composable (onOpenDetails: (Long) -> Unit) -> Unit,
    details: @Composable (Long, onBack: () -> Unit) -> Unit
) {
    NavHost(navController = navController, startDestination = DashboardRoute) {
        composable<DashboardRoute> {
            dashboard { id -> navController.navigate(DetailsRoute(id)) }
        }
        composable<DetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<DetailsRoute>()
            details(route.measurementId) { navController.popBackStack() }
        }
    }
}
