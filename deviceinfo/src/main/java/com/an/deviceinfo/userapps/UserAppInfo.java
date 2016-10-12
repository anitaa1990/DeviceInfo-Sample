package com.an.deviceinfo.userapps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.List;

public class UserAppInfo {

    private Context context;
    public UserAppInfo(Context context) {
        this.context = context;
    }

    public List<UserApps> getInstalledApps(boolean getSysPackages) {
        List<UserApps> res = new ArrayList<UserApps>();
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo a = p.applicationInfo;
            if ((!getSysPackages) && ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1)) {
                continue;
            }
            UserApps newInfo = new UserApps();
            newInfo.setAppName(p.applicationInfo.loadLabel(context.getPackageManager()).toString());
            newInfo.setPackageName(p.packageName);
            newInfo.setVersionName(p.versionName);
            newInfo.setVersionCode(p.versionCode);
            res.add(newInfo);
        }
        return res;
    }
}
