package com.chen.mygooglemap.models

import com.chen.mygooglemap.domain.models.LocationDomainModel

/**
 * Data model for current location
 */
data class LocationModel(
    val latitude: Double,
    val longitude: Double
)

fun LocationDomainModel.mapToPresentation(): LocationModel {
    return LocationModel(latitude, longitude)
}