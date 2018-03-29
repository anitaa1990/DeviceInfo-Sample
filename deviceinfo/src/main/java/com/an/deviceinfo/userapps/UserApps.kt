package com.an.deviceinfo.userapps


import org.json.JSONObject

import java.io.Serializable


class UserApps : Serializable {

    var appName: String? = null
    var packageName: String? = null
    var versionName: String? = null
    var versionCode: Int = 0

    fun toJSON(): JSONObject? {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("appName", appName)
            jsonObject.put("packageName", packageName)
            jsonObject.put("versionName", versionName)
            jsonObject.put("versionCode", versionCode)
            return jsonObject

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
