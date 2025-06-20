package com.example.permissionutils

import android.Manifest
import android.app.Fragment
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

object PermissionUtils {

    fun isLocationPermissionGranted(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(fragment: Fragment, requestCode: Int) {
        fragment.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestCode
        )
    }
}