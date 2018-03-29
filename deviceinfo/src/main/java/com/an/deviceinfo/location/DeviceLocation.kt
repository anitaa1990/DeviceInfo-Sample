package com.an.deviceinfo.location

import java.io.Serializable

class DeviceLocation : Serializable {

    var latitude: Double? = null

    var longitude: Double? = null

    var addressLine1: String? = null

    var city: String? = null

    var state: String? = null

    var countryCode: String? = null

    var postalCode: String? = null
}
