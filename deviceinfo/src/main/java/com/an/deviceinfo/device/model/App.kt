package com.an.deviceinfo.device.model

import android.content.Context
import com.an.deviceinfo.device.DeviceInfo
import org.json.JSONObject

class App(context: Context) {

    var appVersionName: String? = null
    var appVersionCode: Int? = null
    var packageName: String? = null
    var activityName: String? = null
    var appName: String? = null

    init {
        val deviceInfo = DeviceInfo(context)
        this.appVersionName = deviceInfo.versionName
        this.appVersionCode = deviceInfo.versionCode
        this.packageName = deviceInfo.packageName
        this.activityName = deviceInfo.activityName
        this.appName = deviceInfo.appName
    }

    fun toJSON(): JSONObject? {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("appVersionName", appVersionName)
            jsonObject.put("appVersionCode", appVersionCode)
            jsonObject.put("packageName", packageName)
            jsonObject.put("activityName", activityName)
            jsonObject.put("appName", appName)
            return jsonObject
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
