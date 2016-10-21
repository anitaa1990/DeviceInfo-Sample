# DeviceInfo-Sample

Simple, single class wrapper to get device information from an android device.

This library provides an easy way to access all the device information without having to deal with all the boilerplate stuff going on inside.

Library also provides option to ask permissions for Marshmellow devices! 

<img src="https://camo.githubusercontent.com/7a097bb07d47506d643804b222bb8ad2be336498/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4150492d392532422d6f72616e67652e7376673f7374796c653d666c6174" alt="API" data-canonical-src="https://img.shields.io/badge/API-9%2B-orange.svg?style=flat" style="max-width:100%;">

<h2>Downloads</h2>
You can download the aar file from the release folder in this project.</br>
In order to import a .aar library:</br>
1) Go to File>New>New Module</br>
2) Select "Import .JAR/.AAR Package" and click next.</br>
3) Enter the path to .aar file and click finish.</br>
4) Go to File>Project Settings (Ctrl+Shift+Alt+S).</br>
5) Under "Modules," in left menu, select "app."</br>
6) Go to "Dependencies tab.</br>
7) Click the green "+" in the upper right corner.</br>
8) Select "Module Dependency"</br>
9) Select the new module from the list.</br>

<h2>Usage</h2>
For easy use, I have split up all the device information by the following:</br>
1. Location</br>
2. Ads</br>
3. App</br>
4. Battery</br>
5. Device</br>
6. Memory</br>
7. Network</br>
8. User Installed Apps</br>
9. User Contacts</br>


<h2>Location</h2>
```
LocationInfo locationInfo = new LocationInfo(this);
DeviceLocation location = locationInfo.getLocation();
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| Latitude      | ```getLatitude()``` | Double |
| Longitude      | ```getLongitude()``` | Double |
| Address Line 1      | ```getAddressLine1()``` | String |
| City      | ```getCity()``` | String |
| State      | ```getState()``` | String |
| CountryCode      | ```getCountryCode()``` | String |
| Postal Code      | ```getPostalCode()``` | String |


<h2>Ads</h2>
No Google play services needed!
```
AdInfo adInfo = new AdInfo(this);
adInfo.getAndroidAdId(new new AdInfo.AdIdCallback() {
                         @Override
                         public void onResponse(Ad ad) {
                             String advertisingId = ad.getAdvertisingId();
                             Boolean canTrackAds = ad.isAdDoNotTrack();
                         }
                     });
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| AdvertisingId      | ```getAdvertisingId()``` | String |
| Can Track ads      | ```isAdDoNotTrack()``` | boolean |


<h2>App</h2>
```
App app = new App(this);
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| App Name      | ```getAppName()``` | String |
| Package Name      | ```getPackageName()``` | String |
| Activity Name      | ```getActivityName()``` | String |
| App Version Name      | ```getAppVersionName()``` | String |
| App Version Code      | ```getAppVersionCode()``` | Integer |


<h2>Battery</h2>
```
Battery battery = new Battery(this);
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| Battery Percent      | ```getBatteryPercent()``` | int |
| Is Phone Charging      | ```isPhoneCharging()``` | boolean |
| Battery Health      | ```getBatteryHealth()``` | String |
| Battery Technology      | ```getBatteryTechnology()``` | String |
| Battery Temperature      | ```getBatteryTemperature()``` | float |
| Battery Voltage      | ```getBatteryVoltage()``` | int |
| Charging Source      | ```getChargingSource()``` | String |
| Is Battery Present   | ```isBatteryPresent()``` | boolean |


<h2>Device</h2>
```
Device device = new Device(this);
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| Release Build Version      | ```getReleaseBuildVersion()``` | String |
| Build Version Code Name      | ```getBuildVersionCodeName()``` | String |
| Manufacturer      | ```getManufacturer()``` | String |
| Model      | ```getModel()``` | String |
| Product      | ```getProduct()``` | String |
| Fingerprint      | ```getFingerprint()``` | String |
| Hardware      | ```getHardware()``` | String |
| Radio Version      | ```getRadioVersion()``` | String |
| Device      | ```getDevice()``` | String |
| Board      | ```getBoard()``` | String |
| Display Version      | ```getDisplayVersion()``` | String |
| Build Brand      | ```getBuildBrand()``` | String |
| Build Host      | ```getBuildHost()``` | String |
| Build Time      | ```getBuildTime()``` | long |
| Build User      | ```getBuildUser()``` | String |
| Serial      | ```getSerial()``` | String |
| Os Version      | ```getOsVersion()``` | String |
| Language      | ```getLanguage()``` | String |
| SDK Version      | ```getSdkVersion()``` | int |
| Screen Density      | ```getScreenDensity()``` | String |
| Screen Height      | ```getScreenHeight()``` | int |
| Screen Density      | ```getScreenWidth()``` | int |


<h2>Memory</h2>
```
Memory memory = new Memory(this);
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| Has External SD Card      | ```isHasExternalSDCard()``` | boolean |
| Total RAM      | ```getTotalRAM()``` | long |
| Available Internal Memory Size      | ```getAvailableInternalMemorySize()``` | long |
| Total Internal Memory Size      | ```getTotalInternalMemorySize()``` | long |
| Available External Memory Size      | ```getAvailableExternalMemorySize()``` | long |
| Total External Memory Size      | ```getTotalExternalMemorySize()``` | String |


<h2>Network</h2>
```
Network network = new Network(this);
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| IMEI      | ```getIMEI()``` | String |
| IMSI      | ```getIMSI()``` | String |
| Phone Type      | ```getPhoneType()``` | String |
| Phone Number      | ```getPhoneNumber()``` | String |
| Operator      | ```getOperator()``` | String |
| SIM Serial      | ```getsIMSerial()``` | String |
| Network Class      | ```getNetworkClass()``` | String |
| Network Type      | ```getNetworkType()``` | String |
| Is SIM Locked      | ```isSimNetworkLocked()``` | boolean |
| Is Nfc Present      | ```isNfcPresent()``` | boolean |
| Is Nfc Enabled      | ```isNfcEnabled()``` | boolean |
| Is Wifi Enabled      | ```isWifiEnabled()``` | boolean |
| Is Network Available      | ```isNetworkAvailable()``` | boolean |


<h2>User Installed Apps</h2>
```
UserAppInfo userAppInfo = new UserAppInfo(this);
List<UserApps> userApps = userAppInfo.getInstalledApps(boolean includeSystemApps);
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| App Name      | ```getAppName()``` | String |
| Package Name      | ```getPackageName()``` | String |
| Version Name      | ```getVersionName()``` | String |
| Version Code      | ```getVersionCode()``` | int |


<h2>User Contacts</h2>
```
UserContactInfo userContactInfo = new UserContactInfo(mActivity);
List<UserContacts> userContacts = userContactInfo.getContacts();
```
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| Contact Name      | ```getDisplayName()``` | String |
| Mobile Number      | ```getMobileNumber()``` | String |
| Phone Type      | ```phoneType()``` | String |


<h2>How to get Permissions for android 6+</h2>
Easy! I have provided a small, easy wrapper for getting permissions for marshmellow devices.

First, override onRequestPermissionsResult and call PermissionManager.handleResult(requestCode, permissions, grantResults);
```
PermissionManager permissionManager = new PermissionManager(this);
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.handleResult(requestCode, permissions, grantResults);
    }
```

Now you can ask permission:
```
permissionManager.showPermissionDialog(permission)
                    .withDenyDialogEnabled(true)
                    .withDenyDialogMsg(mActivity.getString(R.string.permission_location))
                    .withCallback(new PermissionManager.PermissionCallback() {
                        @Override
                        public void onPermissionGranted(String[] permissions, int[] grantResults) {
                            //you can handle what to do when permission is granted
                        }

                        @Override
                        public void onPermissionDismissed(String permission) {
                           /**
                             * user has denied the permission. We can display a custom dialog 
                             * to user asking for permission
                           * */
                        }

                        @Override
                        public void onPositiveButtonClicked(DialogInterface dialog, int which) {
                          /**
                            * You can choose to open the
                            * app settings screen
                            * * */
                              PermissionUtils permissionUtils = new PermissionUtils(this);
                              permissionUtils.openAppSettings();
                        }

                        @Override
                        public void onNegativeButtonClicked(DialogInterface dialog, int which) {
                          /**
                            * The user has denied the permission!
                            * You need to handle this in your code
                            * * */
                        }
                    })
                    .build();
```

<h3>Various options available in PermissionManager</h3>
| Value         | Function Name | Returns  |
| ------------- |:-------------:| -----:|
| To enable custom dialog when user has denied the permission    | ```withDenyDialogEnabled()``` | boolean |
| To enable Rationale, explaining the need for the permission, the first time they have denied the permission    | ```withRationaleEnabled()``` | boolean |
| Message to be displayed in the custom dialog   | ```withDenyDialogMsg()``` | String |
| Title to be displayed in the custom dialog    | ```withDenyDialogTitle()``` | String |
| Postive Button text to be displayed in the custom alert dialog    | ```withDenyDialogPosBtnText()``` | String |
| Negative Button text to be displayed in the custom alert dialog    | ```withDenyDialogNegBtnText()``` | String |
| Should display the negative button flag    | ```withDenyDialogNegBtn()``` | boolean |
| Flag to cancel the dialog    | ```isDialogCancellable()``` | boolean |





