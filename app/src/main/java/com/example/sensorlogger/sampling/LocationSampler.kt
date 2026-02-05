package com.example.sensorlogger.sampling

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationSampler(private val context: Context) {

    private val client by lazy { LocationServices.getFusedLocationProviderClient(context) }

    suspend fun getCurrent(): Location? {
        val has = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
        if (!has) return null

        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500L)
            .setMinUpdateIntervalMillis(200L)
            .setMaxUpdateDelayMillis(500L)
            .setWaitForAccurateLocation(false)
            .build()

        return suspendCancellableCoroutine { cont ->
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    client.removeLocationUpdates(this)
                    cont.resume(result.lastLocation)
                }
            }

            client.requestLocationUpdates(request, callback, Looper.getMainLooper())
                .addOnFailureListener {
                    client.removeLocationUpdates(callback)
                    cont.resume(null)
                }

            cont.invokeOnCancellation {
                client.removeLocationUpdates(callback)
            }
        }
    }
}
