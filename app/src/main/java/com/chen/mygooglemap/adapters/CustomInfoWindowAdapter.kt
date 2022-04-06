package com.chen.mygooglemap.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.chen.mygooglemap.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

/**
 * Adapter for custom marker infowindow
 * TBD: add place image and more properties
 */
class CustomInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    private var mWindow = (context as Activity).layoutInflater.inflate(R.layout.infowindow_layout, null)

    private fun rendowWindowText(marker: Marker, view: View){
        val txtTitle = view.findViewById<TextView>(R.id.title)
        val txtSnippet = view.findViewById<TextView>(R.id.snippet)

        txtTitle.text = marker.title
        txtSnippet.text = marker.snippet

        txtTitle.visibility = if (!marker.title.isNullOrEmpty()) View.VISIBLE else View.GONE
        txtSnippet.visibility = if (!marker.snippet.isNullOrEmpty()) View.VISIBLE else View.GONE
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}