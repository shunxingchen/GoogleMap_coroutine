package com.chen.mygooglemap.data.remote

import com.chen.mygooglemap.data.models.NearbyPlacesResponse
import com.chen.mygooglemap.data.models.Place
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * The interface that provides methods to get result of apis
 */

const val BASE_URL = "https://open-api.myhelsinki.fi"
interface LocationAPIs {
    /**
     * Get the nearby places from the API
     */
    @GET("/v2/places/")
    fun getNearbyPlaces(
        @Query("distance_filter") loc_filter: String,
        @Query("language_filter") lang_filter: String,
        @Query("limit") count: Int
    ): Deferred<NearbyPlacesResponse>

    /**
     * Get the specific place from the places
     */
    @GET("/v2/place/{id}")
    fun getPlaceById(
        @Path("id") place_id: String,
    ): Deferred<Place>
}