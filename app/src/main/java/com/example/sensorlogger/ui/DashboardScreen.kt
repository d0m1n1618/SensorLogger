@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sensorlogger.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sensorlogger.data.MeasurementEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    vm: DashboardViewModel,
    onOpenDetails: (Long) -> Unit
) {
    val ui by vm.ui.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Sensor Logger") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (!ui.isMeasuring) vm.measureOnce() }
            ) { Text(if (ui.isMeasuring) "..." else "+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ui.error?.let { Text("Błąd: $it", color = MaterialTheme.colorScheme.error) }

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Statystyki", style = MaterialTheme.typography.titleMedium)
                    Text("Liczba pomiarów: ${ui.count}")
                    Text("Śr. hałas: ${ui.avgNoise.toInt()} / 100")
                    Text("Śr. ruch (peak): ${"%.2f".format(ui.avgAccel)} m/s²")
                    Spacer(Modifier.height(6.dp))
                    Button(
                        onClick = { vm.reset() },
                        enabled = ui.measurements.isNotEmpty()
                    ) { Text("Reset (usuń wszystko)") }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Wykres hałasu (ostatnie 20)", style = MaterialTheme.typography.titleSmall)
                NoiseChart(values = ui.measurements.map { it.noiseDb })
            }

            Text("Pomiary", style = MaterialTheme.typography.titleMedium)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ui.measurements) { m ->
                    MeasurementRow(m = m, onClick = { onOpenDetails(m.id) })
                }
            }
        }
    }
}

@Composable
private fun MeasurementRow(m: MeasurementEntity, onClick: () -> Unit) {
    val fmt = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }
    Card(Modifier.fillMaxWidth().clickable { onClick() }) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(fmt.format(Date(m.timestampMillis)))
            Text(
                "GPS: ${m.latitude?.let { "%.5f".format(it) } ?: "--"}, " +
                        "${m.longitude?.let { "%.5f".format(it) } ?: "--"} (±${m.locationAccuracyM ?: 0f} m)"
            )
            Text("Ruch peak: ${"%.2f".format(m.accelPeak)} m/s²")
            Text("Hałas: ${m.noiseDb.toInt()} / 100")
        }
    }
}
