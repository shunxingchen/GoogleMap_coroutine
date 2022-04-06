package com.chen.mygooglemap.extensions

import com.chen.mygooglemap.R
import com.chen.mygooglemap.models.Tag
import com.chen.mygooglemap.models.Tag.*

/**
 * POIs tags map to icon id
 */

val Tag.iconResourceId: Int?
    get() = when (this) {
        BANK -> R.drawable.ic_bank
        RESTAURANT -> R.drawable.ic_restaurant
        SHOPPING -> R.drawable.ic_shopping
        BAR -> R.drawable.ic_bar
        MUSEUMS -> R.drawable.ic_museum
        HARDWARE_STORE -> R.drawable.ic_hardware_store
        CHURCH -> R.drawable.ic_church
        HOTEL -> R.drawable.ic_lodging
        SIGHTS -> R.drawable.ic_point_of_interest
        THEATRE -> R.drawable.ic_theatre
        HISTORY -> R.drawable.ic_history

        else -> null
    }

fun getTagfromString(tagName: String): Tag? {
    return when(tagName) {
        "bank" -> BANK
        "shopping" -> SHOPPING
        "restaurants" -> RESTAURANT
        "bars and pubs" -> BAR
        "banquet facilities" -> BANQUET
        "meeting places" -> MEETING
        "sights" -> SIGHTS
        "accommodation" -> ACCOMMODATION
        "museums" -> MUSEUMS
        "Hotel" -> HOTEL
        "Church" -> CHURCH
        "Theatre" -> THEATRE
        "Second hand" -> SECOND_HAND
        "hardware store" -> HARDWARE_STORE
        "History" -> HISTORY

        else -> null
    }
}