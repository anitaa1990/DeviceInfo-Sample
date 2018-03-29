package com.an.deviceinfo.permission


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat

class PermissionUtils(private val context: Context) {

    @Synchronized
    fun isPermissionGranted(permission: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        val hasPermission = ContextCompat.checkSelfPermission(context, permission)
        return hasPermission == PackageManager.PERMISSION_GRANTED
    }

    fun openAppSettings() {
        val i = Intent()
        i.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.data = Uri.parse("package:" + context.packageName)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        context.startActivity(i)
    }
}