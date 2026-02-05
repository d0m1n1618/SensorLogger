package com.example.sensorlogger.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun NoiseChart(values: List<Float>, modifier: Modifier = Modifier) {
    val data = values.takeLast(20)
    val lineColor: Color = MaterialTheme.colorScheme.primary
    val axisColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
    val gridColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    val minV = 0f
    val maxV = 100f
    val range = max(1e-3f, maxV - minV)

    Column(modifier.fillMaxWidth()) {
        //opis + oś Y (po lewej) + wykres
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //OŚ Y: etykiety
            Column(
                modifier = Modifier
                    .width(48.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Text("100", style = MaterialTheme.typography.labelSmall)
                Text("50", style = MaterialTheme.typography.labelSmall)
                Text("0", style = MaterialTheme.typography.labelSmall)
            }

            //WYKRES
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(start = 8.dp, end = 12.dp)
            ) {
                Canvas(Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    fun y(v: Float): Float {
                        val clamped = v.coerceIn(minV, maxV)
                        val norm = (clamped - minV) / range
                        return h - (norm * h)
                    }

                    //siatka pozioma 0/50/100
                    val y0 = y(0f)
                    val y50 = y(50f)
                    val y100 = y(100f)

                    drawLine(color = gridColor, start = Offset(0f, y100), end = Offset(w, y100), strokeWidth = 2f)
                    drawLine(color = gridColor, start = Offset(0f, y50), end = Offset(w, y50), strokeWidth = 2f)
                    drawLine(color = gridColor, start = Offset(0f, y0), end = Offset(w, y0), strokeWidth = 2f)

                    // ramka
                    drawLine(color = axisColor, start = Offset(0f, 0f), end = Offset(w, 0f), strokeWidth = 2f)
                    drawLine(color = axisColor, start = Offset(0f, h), end = Offset(w, h), strokeWidth = 2f)
                    drawLine(color = axisColor, start = Offset(0f, 0f), end = Offset(0f, h), strokeWidth = 2f)
                    drawLine(color = axisColor, start = Offset(w, 0f), end = Offset(w, h), strokeWidth = 2f)

                    //linia danych
                    if (data.size >= 2) {
                        val stepX = w / (data.size - 1).toFloat()
                        for (i in 0 until data.size - 1) {
                            val x1 = i * stepX
                            val x2 = (i + 1) * stepX
                            drawLine(
                                color = lineColor,
                                start = Offset(x1, y(data[i])),
                                end = Offset(x2, y(data[i + 1])),
                                strokeWidth = 4f
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 56.dp, end = 12.dp), //wyrównanie do wykresu (48 + 8)
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Starsze", style = MaterialTheme.typography.labelSmall)
            Text("Nowsze", style = MaterialTheme.typography.labelSmall)
        }

        Text(
            text = "Hałas (skala 0–100)",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 56.dp, top = 6.dp)
        )
    }
}
