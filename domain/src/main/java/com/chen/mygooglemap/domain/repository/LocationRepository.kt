package com.chen.mygooglemap.domain.repository

import com.chen.mygooglemap.domain.models.LocationDomainModel
import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.domain.utils.Result
import kotlinx.coroutines.flow.Flow

/**
 * Interface for usecases
 */
interface LocationRepository {
    suspend fun getLocation(): Flow<LocationDomainModel>
    suspend fun getNearPlaces(location: String): Result<List<PlaceDomainModel>>
}