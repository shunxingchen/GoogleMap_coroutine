package com.chen.mygooglemap.domain.usecaes

import com.chen.mygooglemap.domain.models.LocationDomainModel
import com.chen.mygooglemap.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Usecase - get current location
 */
class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend fun build() : Flow<LocationDomainModel> {
        return locationRepository.getLocation()
    }

}