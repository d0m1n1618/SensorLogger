package com.example.sensorlogger.navigation

import kotlinx.serialization.Serializable

@Serializable
object DashboardRoute

@Serializable
data class DetailsRoute(val measurementId: Long)
