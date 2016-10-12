package com.an.deviceinfo.device.model;

import android.content.Context;
import com.an.deviceinfo.device.DeviceInfo;

import org.json.JSONObject;

public class Network {

    private String IMEI;
    private String IMSI;
    private String phoneType;
    private String phoneNumber;
    private String operator;
    private String sIMSerial;
    private boolean isSimNetworkLocked;
    private boolean isNfcPresent;
    private boolean isNfcEnabled;
    private boolean isWifiEnabled;
    private boolean isNetworkAvailable;
    private String networkClass;
    private String networkType;

    public Network(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo(context);
        this.IMEI = deviceInfo.getIMEI();
        this.IMSI = deviceInfo.getIMSI();
        this.phoneType = deviceInfo.getPhoneType();
        this.phoneNumber = deviceInfo.getPhoneNumber();
        this.operator = deviceInfo.getOperator();
        this.sIMSerial = deviceInfo.getSIMSerial();
        this.isSimNetworkLocked = deviceInfo.isSimNetworkLocked();
        this.isNfcPresent = deviceInfo.isNfcPresent();
        this.isNfcEnabled = deviceInfo.isNfcEnabled();
        this.isWifiEnabled = deviceInfo.isWifiEnabled();
        this.isNetworkAvailable = deviceInfo.isNetworkAvailable();
        this.networkClass = deviceInfo.getNetworkClass();
        this.networkType = deviceInfo.getNetworkType();
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getsIMSerial() {
        return sIMSerial;
    }

    public void setsIMSerial(String sIMSerial) {
        this.sIMSerial = sIMSerial;
    }

    public boolean isSimNetworkLocked() {
        return isSimNetworkLocked;
    }

    public void setSimNetworkLocked(boolean simNetworkLocked) {
        isSimNetworkLocked = simNetworkLocked;
    }

    public boolean isNfcPresent() {
        return isNfcPresent;
    }

    public void setNfcPresent(boolean nfcPresent) {
        isNfcPresent = nfcPresent;
    }

    public boolean isNfcEnabled() {
        return isNfcEnabled;
    }

    public void setNfcEnabled(boolean nfcEnabled) {
        isNfcEnabled = nfcEnabled;
    }

    public boolean isWifiEnabled() {
        return isWifiEnabled;
    }

    public void setWifiEnabled(boolean wifiEnabled) {
        isWifiEnabled = wifiEnabled;
    }

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public String getNetworkClass() {
        return networkClass;
    }

    public void setNetworkClass(String networkClass) {
        this.networkClass = networkClass;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("IMEI", getIMEI());
            jsonObject.put("IMSI", getIMSI());
            jsonObject.put("phoneType", getPhoneType());
            jsonObject.put("phoneNumber", getPhoneNumber());
            jsonObject.put("operator", getOperator());
            jsonObject.put("sIMSerial", getsIMSerial());
            jsonObject.put("isSimNetworkLocked", isSimNetworkLocked());
            jsonObject.put("isNfcPresent", isNfcPresent());
            jsonObject.put("isNfcEnabled", isNfcEnabled());
            jsonObject.put("isWifiEnabled", isWifiEnabled());
            jsonObject.put("isNetworkAvailable", isNetworkAvailable());
            jsonObject.put("networkClass", getNetworkClass());
            jsonObject.put("networkType", getNetworkType());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
