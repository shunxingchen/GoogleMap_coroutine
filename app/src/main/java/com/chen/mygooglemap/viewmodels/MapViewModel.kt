package com.chen.mygooglemap.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.chen.mygooglemap.domain.models.LocationDomainModel
import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.domain.usecaes.GetLocationUseCase
import com.chen.mygooglemap.domain.usecaes.GetNearPlacesUsecase
import com.chen.mygooglemap.models.LocationModel
import com.chen.mygooglemap.models.mapToPresentation
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model provides data for current location and nearby places
 */
class MapViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getNearByPlacesUsecase: GetNearPlacesUsecase
) : ViewModel() {

    private val location = MutableLiveData<LocationModel>()
    private val places = MutableLiveData<List<PlaceDomainModel>>()
    val currentLocation: LiveData<LocationModel> = location
    val nearByPlaces: LiveData<List<PlaceDomainModel>> = places

    @SuppressLint("CheckResult")
    fun getCurrentLocation() {
        viewModelScope.launch {
            val loc: Flow<LocationDomainModel> = getLocationUseCase.build()

            loc.collect() {
                onGetLocationSuccess(
                    it.mapToPresentation()
                )
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getNearByPlaces(currentMarker: Marker) {
        viewModelScope.launch {
            getNearByPlacesUsecase.build("${currentMarker.position.latitude},${currentMarker.position.longitude},${5}").also {
                if (it.message == null)
                    onGetNearByPlacesSuccess(it.data!!)
                else
                    Log.e("Near places error", it.message!!)
            }
        }
    }

    private fun onGetLocationSuccess(locationModel: LocationModel) {
        location.postValue(locationModel)
    }

    private fun onGetNearByPlacesSuccess(newPlaces: List<PlaceDomainModel>) {
        places.postValue(newPlaces)
    }

    class Factory @Inject constructor(
        private val getLocationUseCase: GetLocationUseCase,
        private val getNearByPlacesUsecase: GetNearPlacesUsecase
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                return MapViewModel(
                    getLocationUseCase,
                    getNearByPlacesUsecase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}