package com.an.deviceinfo.device.model

import android.content.Context
import com.an.deviceinfo.device.DeviceInfo

import org.json.JSONObject

class Network(context: Context) {

    var imei: String? = null
    var imsi: String? = null
    var phoneType: String? = null
    var phoneNumber: String? = null
    var operator: String? = null
    private var sIMSerial: String? = null
    var isSimNetworkLocked: Boolean = false
    var isNfcPresent: Boolean = false
    var isNfcEnabled: Boolean = false
    var isWifiEnabled: Boolean = false
    var isNetworkAvailable: Boolean = false
    var networkClass: String? = null
    var networkType: String? = null

    init {
        val deviceInfo = DeviceInfo(context)
        this.imei = deviceInfo.imei
        this.imsi = deviceInfo.imsi
        this.phoneType = deviceInfo.phoneType
        this.phoneNumber = deviceInfo.phoneNumber
        this.operator = deviceInfo.operator
        this.sIMSerial = deviceInfo.simSerial
        this.isSimNetworkLocked = deviceInfo.isSimNetworkLocked
        this.isNfcPresent = deviceInfo.isNfcPresent
        this.isNfcEnabled = deviceInfo.isNfcEnabled
        this.isWifiEnabled = deviceInfo.isWifiEnabled
        this.isNetworkAvailable = deviceInfo.isNetworkAvailable
        this.networkClass = deviceInfo.networkClass
        this.networkType = deviceInfo.networkType
    }

    fun getsIMSerial(): String? {
        return sIMSerial
    }

    fun setsIMSerial(sIMSerial: String) {
        this.sIMSerial = sIMSerial
    }

    fun toJSON(): JSONObject? {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("IMEI", imei)
            jsonObject.put("IMSI", imsi)
            jsonObject.put("phoneType", phoneType)
            jsonObject.put("phoneNumber", phoneNumber)
            jsonObject.put("operator", operator)
            jsonObject.put("sIMSerial", getsIMSerial())
            jsonObject.put("isSimNetworkLocked", isSimNetworkLocked)
            jsonObject.put("isNfcPresent", isNfcPresent)
            jsonObject.put("isNfcEnabled", isNfcEnabled)
            jsonObject.put("isWifiEnabled", isWifiEnabled)
            jsonObject.put("isNetworkAvailable", isNetworkAvailable)
            jsonObject.put("networkClass", networkClass)
            jsonObject.put("networkType", networkType)
            return jsonObject
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
