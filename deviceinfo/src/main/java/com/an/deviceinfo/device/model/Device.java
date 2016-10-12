package com.an.deviceinfo.device.model;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.an.deviceinfo.device.DeviceInfo;

import org.json.JSONObject;

public class Device {

    private String releaseBuildVersion;
    private String buildVersionCodeName;
    private String manufacturer;
    private String model;
    private String product;
    private String fingerprint;
    private String hardware;
    private String radioVersion;
    private String device;
    private String board;
    private String displayVersion;
    private String buildBrand;
    private String buildHost;
    private long buildTime;
    private String buildUser;
    private String serial;
    private String osVersion;
    private String language;
    private int sdkVersion;
    private String screenDensity;
    private int screenHeight;
    private int screenWidth;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public Device(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo(context);
        this.releaseBuildVersion = deviceInfo.getReleaseBuildVersion();
        this.buildVersionCodeName = deviceInfo.getBuildVersionCodeName();
        this.manufacturer = deviceInfo.getManufacturer();
        this.model = deviceInfo.getModel();
        this.product = deviceInfo.getProduct();
        this.fingerprint = deviceInfo.getFingerprint();
        this.hardware = deviceInfo.getHardware();
        this.radioVersion = deviceInfo.getRadioVer();
        this.device = deviceInfo.getDevice();
        this.board = deviceInfo.getBoard();
        this.displayVersion = deviceInfo.getDisplayVersion();
        this.buildBrand = deviceInfo.getBuildBrand();
        this.buildHost = deviceInfo.getBuildHost();
        this.buildTime = deviceInfo.getBuildTime();
        this.buildUser = deviceInfo.getBuildUser();
        this.serial = deviceInfo.getSerial();
        this.osVersion = deviceInfo.getOSVersion();
        this.language = deviceInfo.getLanguage();
        this.sdkVersion = deviceInfo.getSdkVersion();
        this.screenDensity = deviceInfo.getScreenDensity();
        this.screenHeight = deviceInfo.getScreenHeight();
        this.screenWidth = deviceInfo.getScreenWidth();
    }

    public String getReleaseBuildVersion() {
        return releaseBuildVersion;
    }

    public void setReleaseBuildVersion(String releaseBuildVersion) {
        this.releaseBuildVersion = releaseBuildVersion;
    }

    public String getBuildVersionCodeName() {
        return buildVersionCodeName;
    }

    public void setBuildVersionCodeName(String buildVersionCodeName) {
        this.buildVersionCodeName = buildVersionCodeName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getRadioVersion() {
        return radioVersion;
    }

    public void setRadioVersion(String radioVersion) {
        this.radioVersion = radioVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getDisplayVersion() {
        return displayVersion;
    }

    public void setDisplayVersion(String displayVersion) {
        this.displayVersion = displayVersion;
    }

    public String getBuildBrand() {
        return buildBrand;
    }

    public void setBuildBrand(String buildBrand) {
        this.buildBrand = buildBrand;
    }

    public String getBuildHost() {
        return buildHost;
    }

    public void setBuildHost(String buildHost) {
        this.buildHost = buildHost;
    }

    public long getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(long buildTime) {
        this.buildTime = buildTime;
    }

    public String getBuildUser() {
        return buildUser;
    }

    public void setBuildUser(String buildUser) {
        this.buildUser = buildUser;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(int sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getScreenDensity() {
        return screenDensity;
    }

    public void setScreenDensity(String screenDensity) {
        this.screenDensity = screenDensity;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public JSONObject toJSON() {
        try {
            JSONObject jsonObject= new JSONObject();
            jsonObject.put("releaseBuildVersion", getReleaseBuildVersion());
            jsonObject.put("buildVersionCodeName", getBuildVersionCodeName());
            jsonObject.put("manufacturer", getManufacturer());
            jsonObject.put("model", getModel());
            jsonObject.put("product", getProduct());
            jsonObject.put("fingerprint", getFingerprint());
            jsonObject.put("hardware", getHardware());
            jsonObject.put("radioVersion", getRadioVersion());
            jsonObject.put("device", getDevice());
            jsonObject.put("board", getBoard());
            jsonObject.put("displayVersion", getDisplayVersion());
            jsonObject.put("buildBrand", getBuildBrand());
            jsonObject.put("buildHost", getBuildHost());
            jsonObject.put("buildTime", getBuildTime());
            jsonObject.put("buildUser", getBuildUser());
            jsonObject.put("serial", getSerial());
            jsonObject.put("osVersion", getOsVersion());
            jsonObject.put("language", getLanguage());
            jsonObject.put("sdkVersion", getSdkVersion());
            jsonObject.put("screenDensity", getScreenDensity());
            jsonObject.put("screenHeight", getScreenHeight());
            jsonObject.put("screenWidth", getScreenWidth());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
