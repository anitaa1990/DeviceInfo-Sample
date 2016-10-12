package com.an.deviceinfo.location;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.an.deviceinfo.permission.PermissionUtils;

import java.util.List;
import java.util.Locale;


public class LocationInfo implements LocationListener {

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_BW_UPDATES = 0;

    private Context context;
    private PermissionUtils permissionUtils;
    public LocationInfo(Context context) {
        this.context = context;
        this.permissionUtils = new PermissionUtils(context);
    }

    @SuppressWarnings("MissingPermission")
    private Double[] getLocationLatLong() {
        Double[] latlong = new Double[2];
        latlong[0] = 0.0;
        latlong[1] = 0.0;

        try {
            // Get the location manager
            LocationManager Locationm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // ——–Gps provider—
            Locationm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());

            Location locationGPS = Locationm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                latlong[0] = locationGPS.getLatitude();
                latlong[1] = locationGPS.getLongitude();
                Locationm.removeUpdates(this);
                return latlong;
            }

            // ——–Network provider—
            Locationm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this, Looper.getMainLooper());
            Location locationNet = Locationm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (locationNet != null) {
                latlong[0] = locationNet.getLatitude();
                latlong[1] = locationNet.getLongitude();
                Locationm.removeUpdates(this);
                return latlong;
            }

            Location locationPassive = Locationm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (locationPassive != null) {
                latlong[0] = locationPassive.getLatitude();
                latlong[1] = locationPassive.getLongitude();
                Locationm.removeUpdates(this);
                return latlong;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlong;
    }


    private DeviceLocation getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        DeviceLocation addressInfo = new DeviceLocation();
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                addressInfo.setAddressLine1(address.getAddressLine(0));
                addressInfo.setCity(address.getLocality());
                addressInfo.setPostalCode(address.getPostalCode());
                addressInfo.setState(address.getAdminArea());
                addressInfo.setCountryCode(address.getCountryCode());
                addressInfo.setLatitude(latitude);
                addressInfo.setLongitude(longitude);
                return addressInfo;
            }
        } catch (Exception e) {
            Log.e("", "Unable connect to Geocoder", e);
        } finally {
            return addressInfo;
        }
    }

    public DeviceLocation getLocation() {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))
                throw new RuntimeException("Access Fine Location permission not granted!");

        Double[] latlong = getLocationLatLong();
        return getAddressFromLocation(latlong[0], latlong[1]);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
