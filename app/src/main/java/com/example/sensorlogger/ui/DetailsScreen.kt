@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sensorlogger.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DetailsScreen(
    measurementId: Long,
    vm: DetailsViewModel,
    onBack: () -> Unit
) {
    val ui by vm.ui.collectAsState()
    val scope = rememberCoroutineScope()

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(measurementId) { vm.load(measurementId) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Szczegóły") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Wróć") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ui.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            val item = ui.item ?: return@Column
            val fmt = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(fmt.format(Date(item.timestampMillis)), style = MaterialTheme.typography.titleMedium)
                    Text("Lokalizacja: ${item.latitude ?: "--"}, ${item.longitude ?: "--"}")
                    Text("Dokładność: ${item.locationAccuracyM ?: 0f} m")
                    Text("Akcelerometr peak: ${"%.2f".format(item.accelPeak)} m/s²")
                    Text("Hałas: ${item.noiseDb.toInt()} / 100")
                }
            }

            // ✅ Akcje
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { showDeleteDialog = true }
                ) {
                    Text("Usuń pomiar")
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Usunąć pomiar?") },
            text = { Text("Tej operacji nie da się cofnąć.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        scope.launch {
                            vm.delete(measurementId)
                            onBack()
                        }
                    }
                ) { Text("Usuń") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Anuluj") }
            }
        )
    }
}
