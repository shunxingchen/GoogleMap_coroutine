package com.chen.mygooglemap.data.models

import com.chen.mygooglemap.domain.models.*
import com.google.gson.annotations.SerializedName

/**
 * Remote response model for places
 */
data class NearbyPlacesResponse(
    @SerializedName("data")
    val data: List<Place>
)

data class Place(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: PlaceName,
    @SerializedName("sourceType")
    val sourceType: PlaceSource?,
    @SerializedName("infoUrl")
    val infoUrl: String?,
    @SerializedName("modifiedAt")
    val modifiedAt: String?,
    @SerializedName("location")
    val location: PlaceLoc,
    @SerializedName("description")
    val description: PlaceInfo,
    @SerializedName("tags")
    val tags: List<PlaceTag>,
    @SerializedName("extra_searchwords")
    val extra_searchwords: List<String>,
    @SerializedName("openingHoursUrl")
    val openingHoursUrl: String?,
)

data class PlaceSource(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
)

data class PlaceTag(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
)

data class PlaceName(
    @SerializedName("fi")
    val fi: String?,
    @SerializedName("en")
    val en: String?,
    @SerializedName("sv")
    val sv: String?,
    @SerializedName("zh")
    val zh: String?,
)

data class PlaceLoc(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("address")
    val address: PlaceAddress,
)

data class PlaceAddress(
    @SerializedName("streetAddress")
    val streetAddress: String?,
    @SerializedName("postalCode")
    val postalCode: String?,
    @SerializedName("locality")
    val locality: String?,
    @SerializedName("neighbourhood")
    val neighbourhood: String?,
)

data class PlaceInfo(
    @SerializedName("intro")
    val intro: String,
    @SerializedName("body")
    val body: String,
    @SerializedName("images")
    val images: List<PlaceImage>,
)

data class PlaceImage(
    @SerializedName("url")
    val url: String?,
    @SerializedName("copyrightHolder")
    val copyrightHolder: String?,
    @SerializedName("licenseType")
    val licenseType: Void?,
    @SerializedName("media_id")
    val media_id: String?,
)

fun NearbyPlacesResponse.mapToPlacesModel(): List<PlaceDomainModel> {
    return this.data
            .map {
                PlaceDomainModel(
                    id = it.id,
                    name = PlaceDomainName(
                        fi = it.name.fi,
                        en = it.name.en,
                        sv = it.name.sv,
                        zh = it.name.zh
                    ),
                    sourceType = it.sourceType?.let { it1 ->
                        PlaceDomainSource(
                            id = it1.id,
                            name = it.sourceType.name
                        )
                    },
                    infoUrl = it.infoUrl,
                    modifiedAt = it.modifiedAt,
                    location = PlaceDomainLoc(
                        lat = it.location.lat,
                        lon = it.location.lon,
                        address = PlaceDomainAddress(
                            streetAddress = it.location.address.streetAddress,
                            postalCode = it.location.address.postalCode,
                            locality = it.location.address.locality,
                            neighbourhood = it.location.address.neighbourhood
                        )
                    ),
                    description = PlaceDomainInfo(
                        intro = it.description.intro,
                        body = it.description.body,
                        images = it.description.images.map {
                            PlaceDomainImage(
                                url = it.url,
                                copyrightHolder = it.copyrightHolder,
                                licenseType = it.licenseType,
                                media_id = it.media_id
                            )
                        }),
                    tags = it.tags.map {
                        PlaceDomainTag(
                            id = it.id,
                            name = it.name
                        ) },
                    extra_searchwords = it.extra_searchwords,
                    openingHoursUrl = it.openingHoursUrl
                )
            }
}