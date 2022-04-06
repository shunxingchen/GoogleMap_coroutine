package com.chen.mygooglemap.data.datasource

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.chen.mygooglemap.data.models.mapToPlacesModel
import com.chen.mygooglemap.data.remote.LocationAPIs
import com.chen.mygooglemap.domain.models.LocationDomainModel
import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.domain.utils.Result
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Remote fetch current location and nearby places
 */

private const val LOCATION_REQUEST_INTERVAL = 10000L
private const val LOCATION_REQUEST_FASTEST_INTERVAL = 6000L

@Singleton
class GoogleLocationDataSource @Inject constructor(context: Context) {
    @Inject
    lateinit var locationApis: LocationAPIs

    private val thisContext = context
    private val locationSubject = Channel<LocationDomainModel>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(thisContext)
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = LOCATION_REQUEST_INTERVAL
        fastestInterval = LOCATION_REQUEST_FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locResult: LocationResult) {
            locResult.locations.forEach(::setLocation)
        }
    }

    fun getLocation() : Flow<LocationDomainModel> {
        return locationSubject.apply {
            startLocationUpdates()
        }.receiveAsFlow()
    }

    suspend fun getNearPlaces(location: String): Result<List<PlaceDomainModel>> = fetchNearByPlaces(location)

    private suspend fun fetchNearByPlaces(location: String): Result<List<PlaceDomainModel>> {
        return try {
            locationApis.getNearbyPlaces(location, "en", 13).let {
                Result.Success(it.await().mapToPlacesModel())
            }
        } catch (e: Exception) {
            Result.Error(e.message!!, null)
        }
    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                thisContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                thisContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener(::setLocation)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @DelicateCoroutinesApi
    private fun setLocation(location: Location) {
        GlobalScope.launch {
            locationSubject.send(
                LocationDomainModel(
                    location.latitude,
                    location.longitude
                )
            )
        }
    }

}
