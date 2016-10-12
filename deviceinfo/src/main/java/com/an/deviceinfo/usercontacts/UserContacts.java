package com.an.deviceinfo.usercontacts;


import org.json.JSONObject;

import java.io.Serializable;

public class UserContacts implements Serializable {

    private String displayName;
    private String mobileNumber;
    private String phoneType;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMobileNumber() { return mobileNumber; }

    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getPhoneType() { return phoneType; }

    public void setPhoneType(String phoneType) { this.phoneType = phoneType; }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("displayName", getDisplayName());
            jsonObject.put("mobileNumber", getMobileNumber());
            jsonObject.put("phoneType", getPhoneType());
            return jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}