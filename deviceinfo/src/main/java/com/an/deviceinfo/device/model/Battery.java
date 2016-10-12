package com.an.deviceinfo.device.model;


import android.content.Context;

import com.an.deviceinfo.device.DeviceInfo;

import org.json.JSONObject;

public class Battery {

    private int batteryPercent;
    private boolean isPhoneCharging;
    private String batteryHealth;
    private String batteryTechnology;
    private float batteryTemperature;
    private int batteryVoltage;
    private String chargingSource;
    private boolean isBatteryPresent;

    public Battery(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo(context);
        this.batteryPercent = deviceInfo.getBatteryPercent();
        this.isPhoneCharging = deviceInfo.isPhoneCharging();
        this.batteryHealth = deviceInfo.getBatteryHealth();
        this.batteryTechnology = deviceInfo.getBatteryTechnology();
        this.batteryTemperature = deviceInfo.getBatteryTemperature();
        this.batteryVoltage = deviceInfo.getBatteryVoltage();
        this.chargingSource = deviceInfo.getChargingSource();
        this.isBatteryPresent = deviceInfo.isBatteryPresent();
    }

    public int getBatteryPercent() {
        return batteryPercent;
    }

    public void setBatteryPercent(int batteryPercent) {
        this.batteryPercent = batteryPercent;
    }

    public boolean isPhoneCharging() {
        return isPhoneCharging;
    }

    public void setPhoneCharging(boolean phoneCharging) {
        isPhoneCharging = phoneCharging;
    }

    public String getBatteryHealth() {
        return batteryHealth;
    }

    public void setBatteryHealth(String batteryHealth) {
        this.batteryHealth = batteryHealth;
    }

    public String getBatteryTechnology() {
        return batteryTechnology;
    }

    public void setBatteryTechnology(String batteryTechnology) {
        this.batteryTechnology = batteryTechnology;
    }

    public float getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(float batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public int getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(int batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public String getChargingSource() {
        return chargingSource;
    }

    public void setChargingSource(String chargingSource) {
        this.chargingSource = chargingSource;
    }

    public boolean isBatteryPresent() {
        return isBatteryPresent;
    }

    public void setBatteryPresent(boolean batteryPresent) {
        isBatteryPresent = batteryPresent;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("batteryPercent", getBatteryPercent());
            jsonObject.put("isPhoneCharging", isPhoneCharging());
            jsonObject.put("batteryHealth", getBatteryHealth());
            jsonObject.put("batteryTechnology", getBatteryTechnology());
            jsonObject.put("batteryTemperature", getBatteryTemperature());
            jsonObject.put("batteryVoltage", getBatteryVoltage());
            jsonObject.put("chargingSource", getChargingSource());
            jsonObject.put("isBatteryPresent", isBatteryPresent());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
