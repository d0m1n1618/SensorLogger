@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sensorlogger.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun PermissionGate(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val needed = remember {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO
        )
    }

    fun hasAll(): Boolean = needed.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    var granted by remember { mutableStateOf(hasAll()) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        granted = result.values.all { it }
    }

    if (granted) {
        content()
    } else {
        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text("Wymagane uprawnienia") }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Aplikacja potrzebuje dostępu do GPS i mikrofonu, aby wykonać pomiar.")
                Button(onClick = { launcher.launch(needed) }) {
                    Text("Nadaj uprawnienia")
                }
            }
        }
    }
}
