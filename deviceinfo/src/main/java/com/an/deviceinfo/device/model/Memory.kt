package com.an.deviceinfo.device.model


import android.content.Context

import com.an.deviceinfo.device.DeviceInfo

import org.json.JSONObject

class Memory(context: Context) {

    var isHasExternalSDCard: Boolean = false
    var totalRAM: Long = 0
    var availableInternalMemorySize: Long = 0
    var totalInternalMemorySize: Long = 0
    var availableExternalMemorySize: Long = 0
    var totalExternalMemorySize: Long = 0

    init {
        val deviceInfo = DeviceInfo(context)
        this.isHasExternalSDCard = deviceInfo.hasExternalSDCard()
        this.totalRAM = deviceInfo.totalRAM
        this.availableInternalMemorySize = deviceInfo.availableInternalMemorySize
        this.totalInternalMemorySize = deviceInfo.totalInternalMemorySize
        this.availableExternalMemorySize = deviceInfo.availableExternalMemorySize
        this.totalExternalMemorySize = deviceInfo.totalExternalMemorySize
    }

    fun toJSON(): JSONObject? {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("hasExternalSDCard", isHasExternalSDCard)
            jsonObject.put("totalRAM", totalRAM)
            jsonObject.put("availableInternalMemorySize", availableInternalMemorySize)
            jsonObject.put("totalInternalMemorySize", totalInternalMemorySize)
            jsonObject.put("availableExternalMemorySize", availableExternalMemorySize)
            jsonObject.put("totalExternalMemorySize", totalExternalMemorySize)
            return jsonObject
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}
