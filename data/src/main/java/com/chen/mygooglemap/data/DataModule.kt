package com.chen.mygooglemap.data

import com.chen.mygooglemap.data.datasource.GoogleLocationDataSource
import com.chen.mygooglemap.data.repository.LocationRepositoryImpl
import com.chen.mygooglemap.domain.repository.LocationRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun providesLocationRepository(googleLocationDataSource: GoogleLocationDataSource): LocationRepository {
        return LocationRepositoryImpl(googleLocationDataSource)
    }

}