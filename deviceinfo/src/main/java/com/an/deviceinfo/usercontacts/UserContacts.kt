package com.an.deviceinfo.usercontacts


import org.json.JSONObject

import java.io.Serializable

class UserContacts : Serializable {

    var displayName: String? = null
    var mobileNumber: String? = null
    var phoneType: String? = null

    fun toJSON(): JSONObject? {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("displayName", displayName)
            jsonObject.put("mobileNumber", mobileNumber)
            jsonObject.put("phoneType", phoneType)
            return jsonObject

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}