package com.chen.mygooglemap.domain.usecaes

import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.domain.repository.LocationRepository
import com.chen.mygooglemap.domain.utils.Result
import javax.inject.Inject

/**
 * Usecase - get nearby places
 */
class GetNearPlacesUsecase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend fun build(location: String) : Result<List<PlaceDomainModel>> {
        return locationRepository.getNearPlaces(location)
    }

}