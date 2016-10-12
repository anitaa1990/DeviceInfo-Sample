package com.an.deviceinfo.device.model;

import android.content.Context;
import com.an.deviceinfo.device.DeviceInfo;
import org.json.JSONObject;

public class App {

    private String appVersionName;
    private Integer appVersionCode;
    private String packageName;
    private String activityName;
    private String appName;

    public App(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo(context);
        this.appVersionName = deviceInfo.getVersionName();
        this.appVersionCode = deviceInfo.getVersionCode();
        this.packageName = deviceInfo.getPackageName();
        this.activityName = deviceInfo.getActivityName();
        this.appName = deviceInfo.getAppName();
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public Integer getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(Integer appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("appVersionName", getAppVersionName());
            jsonObject.put("appVersionCode", getAppVersionCode());
            jsonObject.put("packageName", getPackageName());
            jsonObject.put("activityName", getActivityName());
            jsonObject.put("appName", getAppName());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
