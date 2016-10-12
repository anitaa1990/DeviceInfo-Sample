package com.an.deviceinfo.permission;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

public class PermissionUtils {

    private Context context;
    public PermissionUtils(Context context) {
        this.context = context;
    }

    public final boolean isPermissionGranted(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        int hasPermission = ContextCompat.checkSelfPermission(context, permission);
        return hasPermission != PackageManager.PERMISSION_GRANTED;
    }

    public void openAppSettings() {
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }
}
