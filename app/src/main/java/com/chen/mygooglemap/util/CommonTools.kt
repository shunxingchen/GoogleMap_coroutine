package com.chen.mygooglemap.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Common static methods
 */
fun isLocationPermissionGranted(context: Context): Boolean {
    return isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION)
}

private fun isPermissionGranted(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestLocationPermission(fragment: Fragment) {
    requestPermission(fragment, Manifest.permission.ACCESS_FINE_LOCATION, 1)
}

private fun requestPermission(fragment: Fragment, permission: String, requestCode: Int) {
    fragment.requestPermissions(arrayOf(permission), requestCode)
}

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
