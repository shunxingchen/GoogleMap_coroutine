package com.chen.mygooglemap.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chen.mygooglemap.R
import com.chen.mygooglemap.adapters.CustomInfoWindowAdapter
import com.chen.mygooglemap.domain.models.PlaceDomainModel
import com.chen.mygooglemap.extensions.getTagfromString
import com.chen.mygooglemap.extensions.iconResourceId
import com.chen.mygooglemap.models.LocationModel
import com.chen.mygooglemap.models.Tag
import com.chen.mygooglemap.util.getBitmapFromVectorDrawable
import com.chen.mygooglemap.util.isLocationPermissionGranted
import com.chen.mygooglemap.util.requestLocationPermission
import com.chen.mygooglemap.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

/**
 * Map fragment to show current location and nearby POIs
 */

class GMapFragment : BaseFragment<MainActivity>(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    @Inject
    lateinit var viewModelProviderFactory: MapViewModel.Factory
    private lateinit var viewModel: MapViewModel

    companion object {
        fun create(args: Bundle?) : GMapFragment {
            val frag = GMapFragment()

            if (args != null)
                frag.arguments = args

            return frag
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(activity, viewModelProviderFactory)
            .get(MapViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frg) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        with (viewModel) {
            currentLocation.observe(viewLifecycleOwner, Observer(::onLocationChanged))
        }

        with (viewModel) {
            nearByPlaces.observe(viewLifecycleOwner, Observer(::onNearPlacesChanged))
        }

        near_places.setOnClickListener {
            currentMarker?.let { it1 -> viewModel.getNearByPlaces(it1) }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap;
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        mMap.clear()

        if(Build.VERSION.SDK_INT >= 23 && isLocationPermissionGranted(activity)) {
            viewModel.getCurrentLocation()
        }
        else
            requestLocationPermission(this)

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter(activity))
        mMap.setOnMarkerClickListener(this)
    }

    private fun onLocationChanged(locationModel: LocationModel) {
        mMap.apply {
            val latLng = LatLng(locationModel.latitude, locationModel.longitude)

            currentMarker?.remove()

            currentMarker =
            addMarker(MarkerOptions()
                .position(latLng)
                .title(resources.getString(R.string.current_location)))
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.6f))
        }

        if (!near_places.isEnabled)
            near_places.isEnabled = true
    }

    private fun onNearPlacesChanged(places: List<PlaceDomainModel>) {
        if (places.isNotEmpty()) {
            mMap.clear()
            currentMarker = mMap.addMarker(currentMarker?.let {
                MarkerOptions().position(it.position).title(
                    currentMarker!!.title
                )
            })
        } else
            return

        places.forEach {
            val latLng = it.location.latLng
            var thisTagIcon: Tag? = null

            it.tags.forEach {
                val thisTag: Tag? = getTagfromString(it.name)
                if (thisTag != null)
                    thisTagIcon = thisTag
            }

            val marker =
            mMap.addMarker(MarkerOptions()
                .position(latLng)
                .title(it.name.en)
                .snippet(it.description.intro))

            var tagIcon: Int? = null
            if (thisTagIcon != null)
                tagIcon = thisTagIcon!!.iconResourceId

            if (tagIcon != null)
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(activity, tagIcon)))
            else
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty()) {
            when (grantResults.first()) {
                PackageManager.PERMISSION_GRANTED -> viewModel.getCurrentLocation()
            }
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
        }

        return false
    }
}