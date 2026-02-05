package com.example.sensorlogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sensorlogger.di.AppModule
import com.example.sensorlogger.navigation.AppNavGraph
import com.example.sensorlogger.ui.DashboardScreen
import com.example.sensorlogger.ui.DashboardViewModel
import com.example.sensorlogger.ui.DetailsScreen
import com.example.sensorlogger.ui.DetailsViewModel
import com.example.sensorlogger.ui.PermissionGate
import com.example.sensorlogger.SimpleVmFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val module = remember { AppModule(context.applicationContext) }
            val navController = rememberNavController()

            PermissionGate {
                AppNavGraph(
                    navController = navController,
                    dashboard = { onOpenDetails ->
                        val vm = viewModel<DashboardViewModel>(
                            factory = SimpleVmFactory {
                                DashboardViewModel(context.applicationContext, module.repository)
                            }
                        )
                        DashboardScreen(vm = vm, onOpenDetails = onOpenDetails)
                    },
                    details = { id, onBack ->
                        val vm = viewModel<DetailsViewModel>(
                            factory = SimpleVmFactory {
                                DetailsViewModel(module.repository)
                            }
                        )
                        DetailsScreen(measurementId = id, vm = vm, onBack = onBack)
                    }
                )
            }
        }
    }
}
