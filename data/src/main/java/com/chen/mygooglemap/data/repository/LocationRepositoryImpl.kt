package com.chen.mygooglemap.data.repository

import com.chen.mygooglemap.data.datasource.GoogleLocationDataSource
import com.chen.mygooglemap.domain.models.LocationDomainModel
import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.domain.repository.LocationRepository
import com.chen.mygooglemap.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation for usecases
 */
@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val googleLocationDataSource: GoogleLocationDataSource
) : LocationRepository {

    override suspend fun getLocation(): Flow<LocationDomainModel> {
        return googleLocationDataSource
            .getLocation() }

    override suspend fun getNearPlaces(location: String): Result<List<PlaceDomainModel>> {
        return googleLocationDataSource
            .getNearPlaces(location)
    }
}