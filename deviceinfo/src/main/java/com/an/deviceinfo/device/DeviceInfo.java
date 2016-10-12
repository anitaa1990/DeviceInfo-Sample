package com.an.deviceinfo.device;


import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.an.deviceinfo.permission.PermissionUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class DeviceInfo {

    private final String BATTERY_HEALTH_COLD = "cold";
    private final String BATTERY_HEALTH_DEAD = "dead";
    private final String BATTERY_HEALTH_GOOD = "good";
    private final String BATTERY_HEALTH_OVERHEAT = "Over Heat";
    private final String BATTERY_HEALTH_OVER_VOLTAGE = "Over Voltage";
    private final String BATTERY_HEALTH_UNKNOWN = "Unknown";
    private final String BATTERY_HEALTH_UNSPECIFIED_FAILURE = "Unspecified failure";


    private final String BATTERY_PLUGGED_AC = "Charging via AC";
    private final String BATTERY_PLUGGED_USB = "Charging via USB";
    private final String BATTERY_PLUGGED_WIRELESS = "Wireless";
    private final String BATTERY_PLUGGED_UNKNOWN = "Unknown Source";


    private final String RINGER_MODE_NORMAL = "Normal";
    private final String RINGER_MODE_SILENT = "Silent";
    private final String RINGER_MODE_VIBRATE = "Vibrate";


    private final String PHONE_TYPE_GSM = "GSM";
    private final String PHONE_TYPE_CDMA = "CDMA";
    private final String PHONE_TYPE_NONE = "Unknown";


    private final String NETWORK_TYPE_2G = "2G";
    private final String NETWORK_TYPE_3G = "3G";
    private final String NETWORK_TYPE_4G = "4G";
    private final String NETWORK_TYPE_WIFI_WIFIMAX = "WiFi";


    private final String NOT_FOUND_VAL = "unknown";

    private Context context;
    private PermissionUtils permissionUtils;
    public DeviceInfo(Context context) {
        this.context = context;
        this.permissionUtils = new PermissionUtils(context);
    }

    /* Device Info: */
    public final String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }


    public final String getReleaseBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    public final String getBuildVersionCodeName() {
        return Build.VERSION.CODENAME;
    }

    public final String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public final String getModel() {
        return Build.MODEL;
    }


    public  String getProduct() {
        return Build.PRODUCT;
    }

    public final String getFingerprint() {
        return Build.FINGERPRINT;
    }

    public final String getHardware() {
        return Build.HARDWARE;
    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public final String getRadioVer() {
        return  Build.getRadioVersion();
    }


    public final String getDevice() {
        return Build.DEVICE;
    }

    public final String getBoard() {
        return Build.BOARD;
    }

    public final String getDisplayVersion() {
        return Build.DISPLAY;
    }

    public final String getBuildBrand() {
        return Build.BRAND;
    }

    public final String getBuildHost() {
        return Build.HOST;
    }

    public final long getBuildTime() {
        return Build.TIME;
    }

    public final String getBuildUser() {
        return Build.USER;
    }

    public final String getSerial() {
        return Build.SERIAL;
    }

    public final String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public final String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public final int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public String getScreenDensity() {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        String scrType = "";
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                scrType = "ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                scrType = "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                scrType = "hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                scrType = "xhdpi";
                break;
            default:
                scrType = "other";
                break;
        }
        return scrType;
    }

    public int getScreenHeight() {
        int height = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            height = display.getHeight();  // deprecated
        }
        return height;
    }

    public int getScreenWidth() {
        int width = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();  // deprecated
        }
        return width;
    }


    /* App Info: */
    public String getVersionName() {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e1) {
            return null;
        }
    }

    public Integer getVersionCode() {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (Exception e1) {
            return null;
        }
    }

    public String getPackageName() {
        return context.getPackageName();
    }

    public String getActivityName() {
        return context.getClass().getSimpleName();
    }

    public String getAppName() {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : NOT_FOUND_VAL);
    }

    public final boolean isAppInstalled(String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }



    /* Battery Info:
     * battery percentage
     * is phone charging at the moment
     * Battery Health
     * Battery Technology
     * Battery Temperature
     * Battery Voltage
     * Charging Source
     * Check if battery is present */
    private Intent getBatteryStatusIntent() {
        IntentFilter batFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return context.registerReceiver(null, batFilter);
    }

    public int getBatteryPercent() {
        Intent intent = getBatteryStatusIntent();
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = (rawlevel * 100) / scale;
        }
        return level;
    }

    public boolean isPhoneCharging() {
        Intent intent = getBatteryStatusIntent();
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }

    public String getBatteryHealth() {
        String health = BATTERY_HEALTH_UNKNOWN;
        Intent intent = getBatteryStatusIntent();
        int status = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
        switch (status) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                health = BATTERY_HEALTH_COLD;
                break;

            case BatteryManager.BATTERY_HEALTH_DEAD:
                health = BATTERY_HEALTH_DEAD;
                break;

            case BatteryManager.BATTERY_HEALTH_GOOD:
                health = BATTERY_HEALTH_GOOD;
                break;

            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health = BATTERY_HEALTH_OVERHEAT;
                break;

            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health = BATTERY_HEALTH_OVER_VOLTAGE;
                break;

            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                health = BATTERY_HEALTH_UNKNOWN;
                break;

            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                health = BATTERY_HEALTH_UNSPECIFIED_FAILURE;
                break;
        }
        return health;
    }

    public final String getBatteryTechnology() {
        Intent intent = getBatteryStatusIntent();
        return intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
    }

    public final float getBatteryTemperature() {
        Intent intent = getBatteryStatusIntent();
        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
        return (float) (temperature / 10.0);
    }

    public final int getBatteryVoltage() {
        Intent intent = getBatteryStatusIntent();
        return intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
    }

    public final String getChargingSource() {
        Intent intent = getBatteryStatusIntent();
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return BATTERY_PLUGGED_AC;
            case BatteryManager.BATTERY_PLUGGED_USB:
                return BATTERY_PLUGGED_USB;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return BATTERY_PLUGGED_WIRELESS;
            default:
                return BATTERY_PLUGGED_UNKNOWN;
        }
    }

    public final boolean isBatteryPresent() {
        Intent intent = getBatteryStatusIntent();
        return intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
    }


    /* Id Info: */
    @SuppressWarnings("MissingPermission")
    public final String getBluetoothMAC() {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.BLUETOOTH))
              throw new RuntimeException("Access Bluetooth permission not granted!");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.Secure.getString(context.getContentResolver(),
                    "bluetooth_address");
        } else {
            BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
            String result = bta != null ? bta.getAddress() : "00";
            return result;
        }
    }

    @SuppressWarnings("MissingPermission")
    public final String getWifiMacAddress(Context context) {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.ACCESS_WIFI_STATE))
                throw new RuntimeException("Access Wifi state permission not granted!");

        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        return address;
    }

    public boolean isRunningOnEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || Build.PRODUCT.contains("vbox86p")
                || Build.DEVICE.contains("vbox86p")
                || Build.HARDWARE.contains("vbox86");
    }

    public String getDeviceRingerMode() {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                return RINGER_MODE_SILENT;
            case AudioManager.RINGER_MODE_VIBRATE:
                return RINGER_MODE_VIBRATE;
            default:
                return RINGER_MODE_NORMAL;
        }
    }

    public final boolean isDeviceRooted() {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }


    @SuppressWarnings("MissingPermission")
    public final List<String> getEmailAccounts() {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.GET_ACCOUNTS))
                throw new RuntimeException("Get Accounts permission not granted!");

        Set<String> emails = new HashSet<String>();
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                emails.add(account.name);
            }
        }
        List list = new ArrayList<String>(new LinkedHashSet<String>(emails));
        return list;
    }

    public final String getAndroidId() {
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public  String getInstallSource(){
        PackageManager pm = context.getPackageManager();
        String installationSource = pm.getInstallerPackageName(context.getPackageName());
        return installationSource;
    }

    public final String getUserAgent() {
        final String systemUa = System.getProperty("http.agent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return WebSettings.getDefaultUserAgent(context) + "__" + systemUa;
        }
         return new WebView(context).getSettings().getUserAgentString() + "__" + systemUa;
    }

    public final String getGSFId() {
        Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        String ID_KEY = "android_id";
        String params[] = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);

        if (!c.moveToFirst() || c.getColumnCount() < 2) {
            c.close();
            return NOT_FOUND_VAL;
        }
        try {
            String gsfId =  Long.toHexString(Long.parseLong(c.getString(1)));
            c.close();
            return gsfId;
        }
        catch (NumberFormatException e) {
            c.close();
            return NOT_FOUND_VAL;
        }
    }


    public boolean hasExternalSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public final long getTotalRAM() {
        long totalMemory = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            return mi.totalMem;
        }
    try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            String load = reader.readLine().replaceAll("\\D+", "");
            totalMemory = (long) Integer.parseInt(load);
            reader.close();
            return totalMemory;
        } catch (IOException e) {
            e.printStackTrace();
            return 0l;
        }
    }

    public final long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize, availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }
        return availableBlocks * blockSize;
    }

    public final long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
        }
        return totalBlocks * blockSize;
    }


    public final long getAvailableExternalMemorySize() {
        if (hasExternalSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            long availableBlocks;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
                availableBlocks = stat.getAvailableBlocksLong();
            } else {
                blockSize = stat.getBlockSize();
                availableBlocks = stat.getAvailableBlocks();
            }
            return availableBlocks * blockSize;
        }
        return 0;
    }


    public final long getTotalExternalMemorySize() {
        if (hasExternalSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize;
            long totalBlocks;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getBlockSizeLong();
                totalBlocks = stat.getBlockCountLong();
            } else {
                blockSize = stat.getBlockSize();
                totalBlocks = stat.getBlockCount();
            }
            return totalBlocks * blockSize;
        }
        return 0;
    }

    public final String getIMEI() {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.READ_PHONE_STATE))
                throw new RuntimeException("Read Phone State permission not granted!");

        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        return telephonyMgr.getDeviceId();
    }

    public final String getIMSI() {
        TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        return telephonyMgr.getSubscriberId();
    }

    public final String getPhoneType() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_GSM:
                return PHONE_TYPE_GSM;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return PHONE_TYPE_CDMA;
            case TelephonyManager.PHONE_TYPE_NONE:
            default:
                return PHONE_TYPE_NONE;
        }
    }

    @SuppressWarnings("MissingPermission")
    public String getPhoneNumber() {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.READ_PHONE_STATE))
                throw new RuntimeException("Read Phone State permission not granted!");

        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) context.getSystemService(serviceName);
        return m_telephonyManager.getLine1Number();
    }


    public String getOperator() {
        String operatorName;
        TelephonyManager telephonyManager =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        operatorName = telephonyManager.getNetworkOperatorName();
        if(operatorName == null)
            operatorName = telephonyManager.getSimOperatorName();
        return operatorName;
    }

    public final String getSIMSerial() {
        TelephonyManager telephonyManager =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            return telephonyManager.getSimSerialNumber();
    }

    public final boolean isSimNetworkLocked() {
        TelephonyManager telephonyManager =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getSimState() == TelephonyManager.SIM_STATE_NETWORK_LOCKED;
    }


    public final boolean isNfcPresent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
            return nfcAdapter != null;
        }
        return false;
    }

    public final boolean isNfcEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
            return nfcAdapter != null && nfcAdapter.isEnabled();
        }
        return false;
    }

    public final boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }

    @SuppressWarnings("MissingPermission")
    public final boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public String getNetworkClass() {
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_TYPE_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_TYPE_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_TYPE_4G;
            default:
                return NOT_FOUND_VAL;
        }
    }

    public final String getNetworkType() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) return NOT_FOUND_VAL;

        else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                || activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX) {
            return NETWORK_TYPE_WIFI_WIFIMAX;
        }
        else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            return getNetworkClass();
        }
        return NOT_FOUND_VAL;
    }
}
