package com.an.deviceinfo.ads;

import android.content.Context;

import java.io.IOException;

public class AdInfo {

    private Context context;
    public AdInfo(Context context) {
        this.context = context;
    }

    public final void getAndroidAdId(final AdIdCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    AdvertisingIdClient.AdIdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    String advertisingId = adInfo.getId();
                    boolean adDoNotTrack = adInfo.isLimitAdTrackingEnabled();

                    //Send Data to callback
                    Ad ad = new Ad();
                    ad.setAdDoNotTrack(adDoNotTrack);
                    ad.setAdvertisingId(advertisingId);
                    callback.onResponse(ad);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface AdIdCallback {
        void onResponse(Ad ad);
    }
}
