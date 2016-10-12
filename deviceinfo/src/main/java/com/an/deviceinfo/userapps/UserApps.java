package com.an.deviceinfo.userapps;


import org.json.JSONObject;

import java.io.Serializable;


public class UserApps implements Serializable {

    private String appName;
    private String packageName;
    private String versionName;
    private int versionCode;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appName", getAppName());
            jsonObject.put("packageName", getPackageName());
            jsonObject.put("versionName", getVersionName());
            jsonObject.put("versionCode", getVersionCode());
            return jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
