package com.chen.mygooglemap.domain.models

import com.google.android.gms.maps.model.LatLng

/**
 * Place data model
 */
data class PlaceDomainModel(
    val id: String,
    val name: PlaceDomainName,
    val sourceType: PlaceDomainSource?,
    val infoUrl: String?,
    val modifiedAt: String?,
    val location: PlaceDomainLoc,
    val description: PlaceDomainInfo,
    val tags: List<PlaceDomainTag>,
    val extra_searchwords: List<String>,
    val openingHoursUrl: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (other !is PlaceDomainModel) {
            return false
        }
        return this.id == other.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }
}

data class PlaceDomainSource(
    val id: String,
    val name: String,
)

data class PlaceDomainTag(
    val id: String,
    val name: String,
)

data class PlaceDomainName(
    val fi: String?,
    val en: String?,
    val sv: String?,
    val zh: String?,
)

data class PlaceDomainLoc(
    val lat: Double,
    val lon: Double,
    val address: PlaceDomainAddress,
) {
    val latLng: LatLng
        get() = LatLng(lat, lon)
}

data class PlaceDomainAddress(
    val streetAddress: String?,
    val postalCode: String?,
    val locality: String?,
    val neighbourhood: String?,
)

data class PlaceDomainInfo(
    val intro: String,
    val body: String,
    val images: List<PlaceDomainImage>,
)

data class PlaceDomainImage(
    val url: String?,
    val copyrightHolder: String?,
    val licenseType: Void?,
    val media_id: String?,
)