package com.an.deviceinfo.device.model


import android.content.Context

import com.an.deviceinfo.device.DeviceInfo

import org.json.JSONObject

class Battery(context: Context) {

    var batteryPercent: Int = 0
    var isPhoneCharging: Boolean = false
    var batteryHealth: String? = null
    var batteryTechnology: String? = null
    var batteryTemperature: Float = 0.toFloat()
    var batteryVoltage: Int = 0
    var chargingSource: String? = null
    var isBatteryPresent: Boolean = false

    init {
        val deviceInfo = DeviceInfo(context)
        this.batteryPercent = deviceInfo.batteryPercent
        this.isPhoneCharging = deviceInfo.isPhoneCharging
        this.batteryHealth = deviceInfo.batteryHealth
        this.batteryTechnology = deviceInfo.batteryTechnology
        this.batteryTemperature = deviceInfo.batteryTemperature
        this.batteryVoltage = deviceInfo.batteryVoltage
        this.chargingSource = deviceInfo.chargingSource
        this.isBatteryPresent = deviceInfo.isBatteryPresent
    }

    fun toJSON(): JSONObject? {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("batteryPercent", batteryPercent)
            jsonObject.put("isPhoneCharging", isPhoneCharging)
            jsonObject.put("batteryHealth", batteryHealth)
            jsonObject.put("batteryTechnology", batteryTechnology)
            jsonObject.put("batteryTemperature", batteryTemperature.toDouble())
            jsonObject.put("batteryVoltage", batteryVoltage)
            jsonObject.put("chargingSource", chargingSource)
            jsonObject.put("isBatteryPresent", isBatteryPresent)
            return jsonObject
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
