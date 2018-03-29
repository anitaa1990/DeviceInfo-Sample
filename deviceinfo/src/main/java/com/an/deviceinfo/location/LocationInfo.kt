package com.an.deviceinfo.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log

import com.an.deviceinfo.permission.PermissionUtils
import java.util.Locale


@SuppressLint("MissingPermission")
class LocationInfo(private val context: Context) : LocationListener {
    private val permissionUtils: PermissionUtils

    private// Get the location manager
            // ——–Gps provider—
            // ——–Network provider—
    val locationLatLong: Array<Double?>
        get() {
            val latlong = arrayOfNulls<Double>(2)
            latlong[0] = 0.0
            latlong[1] = 0.0

            try {
                val Locationm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                Locationm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this, Looper.getMainLooper())

                val locationGPS = Locationm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (locationGPS != null) {
                    latlong[0] = locationGPS.latitude
                    latlong[1] = locationGPS.longitude
                    Locationm.removeUpdates(this)
                    return latlong
                }
                Locationm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this, Looper.getMainLooper())
                val locationNet = Locationm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (locationNet != null) {
                    latlong[0] = locationNet.latitude
                    latlong[1] = locationNet.longitude
                    Locationm.removeUpdates(this)
                    return latlong
                }

                val locationPassive = Locationm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
                if (locationPassive != null) {
                    latlong[0] = locationPassive.latitude
                    latlong[1] = locationPassive.longitude
                    Locationm.removeUpdates(this)
                    return latlong
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return latlong
        }

    val location: DeviceLocation
        get() {
            if (!permissionUtils.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))
                throw RuntimeException("Access Fine Location permission not granted!")

            val latlong = locationLatLong
            return getAddressFromLocation(latlong[0]!!, latlong[1]!!)
        }

    init {
        this.permissionUtils = PermissionUtils(context)
    }


    private fun getAddressFromLocation(latitude: Double, longitude: Double): DeviceLocation {

        val geocoder = Geocoder(context, Locale.getDefault())
        val addressInfo = DeviceLocation()
        try {
            val addressList = geocoder.getFromLocation(latitude, longitude, 1)

            if (addressList != null && addressList.size > 0) {
                val address = addressList[0]
                addressInfo.addressLine1 = address.getAddressLine(0)
                addressInfo.city = address.locality
                addressInfo.postalCode = address.postalCode
                addressInfo.state = address.adminArea
                addressInfo.countryCode = address.countryCode
                addressInfo.latitude = latitude
                addressInfo.longitude = longitude
                return addressInfo
            }
        } catch (e: Exception) {
            Log.e("", "Unable connect to Geocoder", e)
        } finally {
            return addressInfo
        }
    }

    override fun onLocationChanged(location: Location) {

    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    companion object {

        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 0
        private val MIN_TIME_BW_UPDATES: Long = 0
    }
}
