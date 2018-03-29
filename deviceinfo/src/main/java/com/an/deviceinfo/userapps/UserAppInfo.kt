package com.an.deviceinfo.userapps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo

import java.util.ArrayList

class UserAppInfo(private val context: Context) {

    fun getInstalledApps(getSysPackages: Boolean): List<UserApps> {
        val res = ArrayList<UserApps>()
        val packs = context.packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            val a = p.applicationInfo
            if (!getSysPackages && a.flags and ApplicationInfo.FLAG_SYSTEM == 1) {
                continue
            }
            val newInfo = UserApps()
            newInfo.appName = p.applicationInfo.loadLabel(context.packageManager).toString()
            newInfo.packageName = p.packageName
            newInfo.versionName = p.versionName
            newInfo.versionCode = p.versionCode
            res.add(newInfo)
        }
        return res
    }
}
