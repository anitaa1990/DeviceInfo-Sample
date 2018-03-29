package com.an.deviceinfo.ads

import android.content.Context

import java.io.IOException

class AdInfo(private val context: Context) {

    //Send Data to callback
    val ad: Ad
        @Throws(Exception::class)
        get() {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
            val advertisingId = adInfo.id
            val adDoNotTrack = adInfo.isLimitAdTrackingEnabled
            val ad = Ad()
            ad.isAdDoNotTrack = adDoNotTrack
            ad.advertisingId = advertisingId
            return ad
        }

    fun getAndroidAdId(callback: AdIdCallback) {
        Thread(Runnable {
            try {
                val ad = ad
                callback.onResponse(context, ad)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    interface AdIdCallback {
        fun onResponse(context: Context, ad: Ad)
    }
}
