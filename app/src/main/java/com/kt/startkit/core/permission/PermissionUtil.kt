package com.kt.startkit.core.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionUtil {

    /**
     * 앱에서 요청해야 할 모든 권한들의 집합
     */
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val locationPermissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    fun isAllPermissionsGranted(activity: Activity): Boolean {
        for(permission in permissions) {
            if(!hasPermission(activity, permission)) {
                return false
            }
        }
        return true
    }

    fun hasPermission(activity: Activity, permission: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, permission)
    }
}