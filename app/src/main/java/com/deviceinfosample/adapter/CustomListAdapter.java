package com.deviceinfosample.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.an.deviceinfo.ads.Ad;
import com.an.deviceinfo.device.model.App;
import com.an.deviceinfo.device.model.Battery;
import com.an.deviceinfo.device.model.Device;
import com.an.deviceinfo.device.model.Memory;
import com.an.deviceinfo.device.model.Network;
import com.an.deviceinfo.location.DeviceLocation;
import com.an.deviceinfo.userapps.UserApps;
import com.an.deviceinfo.usercontacts.UserContacts;
import com.deviceinfosample.R;

import java.util.List;


public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.CustomViewHolder> {

    private Context context;
    private List deviceList;
    private Object object;

    public CustomListAdapter(Context context, List deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }



    public CustomListAdapter(Context context, Object object) {
        this.context = context;
        this.object = object;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_item, null);
        CustomListAdapter.CustomViewHolder viewHolder = new CustomListAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        if(deviceList == null) {
            if(object instanceof Ad) {
                handleAdInfo(holder, position);
            } else if(object instanceof DeviceLocation) {
                handleLocationInfo(holder, position);
            }  else if(object instanceof App) {
                handleAppInfo(holder, position);
            }  else if(object instanceof Battery) {
                handleBatteryInfo(holder, position);
            }   else if(object instanceof Memory) {
                handleMemoryInfo(holder, position);
            }   else if(object instanceof Network) {
                handleNetworkInfo(holder, position);
            }   else if(object instanceof Device) {
                handleDeviceInfo(holder, position);
            }
            return;
        }

        Object object = deviceList.get(position);
        if(object instanceof UserApps) {
            holder.textView.setText(((UserApps) object).getAppName());
            holder.desc.setText(((UserApps) object).getPackageName());
        } else {
            holder.textView.setText(((UserContacts) object).getDisplayName());
            holder.desc.setText(((UserContacts) object).getMobileNumber());
        }
    }

    @Override
    public int getItemCount() {
        if(deviceList == null) return object.getClass().getDeclaredFields().length;
        return deviceList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private TextView desc;
        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.desc = (TextView) itemView.findViewById(R.id.textDesc);
        }
    }



    private void handleAdInfo(CustomViewHolder holder, int position) {
        if(position == 0) {
            holder.textView.setText("AdvertisingId:");
            holder.desc.setText(((Ad) object).getAdvertisingId());
        } else {
            holder.textView.setText("Allow to track ads:");
            holder.desc.setText(String.valueOf(((Ad) object).isAdDoNotTrack()));
        }
    }

    private void handleLocationInfo(CustomViewHolder holder, int position) {
        switch (position) {
            case 0:  holder.textView.setText("Lattitude:");
                     holder.desc.setText(String.valueOf(((DeviceLocation) object).getLatitude()));
                break;
            case 1: holder.textView.setText("Longitude:");
                    holder.desc.setText(String.valueOf(((DeviceLocation) object).getLongitude()));
                break;
            case 2: holder.textView.setText("Address Line 1:");
                    holder.desc.setText(((DeviceLocation) object).getAddressLine1());
                break;
            case 3: holder.textView.setText("City:");
                    holder.desc.setText(((DeviceLocation) object).getCity());
                break;
            case 4: holder.textView.setText("State:");
                    holder.desc.setText(((DeviceLocation) object).getState());
                break;
            case 5: holder.textView.setText("CountryCode:");
                    holder.desc.setText(((DeviceLocation) object).getCountryCode());
                break;
            case 6: holder.textView.setText("Postal Code:");
                    holder.desc.setText(((DeviceLocation) object).getPostalCode());
                break;
        }
    }

    private void handleAppInfo(CustomViewHolder holder, int position) {
        switch (position) {
            case 0:  holder.textView.setText("App Name:");
                     holder.desc.setText(((App) object).getAppName());
                break;
            case 1: holder.textView.setText("Package Name:");
                    holder.desc.setText(((App) object).getPackageName());
                break;
            case 2: holder.textView.setText("Activity Name:");
                    holder.desc.setText(((App) object).getActivityName());
                break;
            case 3: holder.textView.setText("App Version Name:");
                    holder.desc.setText(((App) object).getAppVersionName());
                break;
            case 4: holder.textView.setText("App Version Code:");
                    holder.desc.setText(String.valueOf(((App) object).getAppVersionCode()));
                break;
        }
    }

    private void handleBatteryInfo(CustomViewHolder holder, int position) {
        switch (position) {
            case 0:  holder.textView.setText("Battery Percent:");
                     holder.desc.setText(String.valueOf(((Battery) object).getBatteryPercent()));
                break;
            case 1: holder.textView.setText("Is Phone Charging:");
                    holder.desc.setText(String.valueOf(((Battery) object).isPhoneCharging()));
                break;
            case 2: holder.textView.setText("Battery Health:");
                    holder.desc.setText(((Battery) object).getBatteryHealth());
                break;
            case 3: holder.textView.setText("Battery Technology:");
                    holder.desc.setText(((Battery) object).getBatteryTechnology());
                break;
            case 4: holder.textView.setText("Battery Temperature:");
                    holder.desc.setText(String.valueOf(((Battery) object).getBatteryTemperature()));
                break;
            case 5: holder.textView.setText("Battery Voltage:");
                    holder.desc.setText(String.valueOf(((Battery) object).getBatteryVoltage()));
                break;
            case 6: holder.textView.setText("Charging Source:");
                holder.desc.setText(((Battery) object).getChargingSource());
                break;
            case 7: holder.textView.setText("Is Battery Present:");
                holder.desc.setText(String.valueOf(((Battery) object).isBatteryPresent()));
                break;
        }
    }

    private void handleMemoryInfo(CustomViewHolder holder, int position) {
        switch (position) {
            case 0:  holder.textView.setText("Has external Momeny Card:");
                     holder.desc.setText(String.valueOf(((Memory) object).isHasExternalSDCard()));
                break;
            case 1: holder.textView.setText("Total RAM:");
                    holder.desc.setText(String.valueOf(convertToGb(((Memory) object).getTotalRAM())) + " GB");
                break;
            case 2: holder.textView.setText("Total Internal Memory Space:");
                    holder.desc.setText(String.valueOf(convertToGb(((Memory) object).getTotalInternalMemorySize()))  + " GB");
                break;
            case 3: holder.textView.setText("Available Memory Space:");
                    holder.desc.setText(String.valueOf(convertToGb(((Memory) object).getAvailableInternalMemorySize()))  + " GB");
                break;
            case 4: holder.textView.setText("Total External Memory Space:");
                    holder.desc.setText(String.valueOf(convertToGb( ((Memory) object).getTotalExternalMemorySize()) ) + " GB");
                break;
            case 5: holder.textView.setText("Available External Momory Space:");
                    holder.desc.setText(String.valueOf(convertToGb (((Memory) object).getAvailableExternalMemorySize()) ) + " GB");
                break;
        }
    }

    private void handleNetworkInfo(CustomViewHolder holder, int position) {
        switch (position) {
            case 0:  holder.textView.setText("IMEI:");
                     holder.desc.setText(((Network) object).getIMEI());
                break;
            case 1: holder.textView.setText("IMSI:");
                    holder.desc.setText(((Network) object).getIMSI());
                break;
            case 2: holder.textView.setText("Phone Type:");
                    holder.desc.setText(((Network) object).getPhoneType());
                break;
            case 3: holder.textView.setText("Phone Number:");
                    holder.desc.setText(((Network) object).getPhoneNumber());
                break;
            case 4: holder.textView.setText("Carrier:");
                    holder.desc.setText(((Network) object).getOperator());
                break;
            case 5: holder.textView.setText("SIM Serial:");
                    holder.desc.setText(((Network) object).getsIMSerial());
                break;
            case 6: holder.textView.setText("is SIM Locked:");
                    holder.desc.setText(String.valueOf(((Network) object).isSimNetworkLocked()));
                break;
            case 7: holder.textView.setText("is Nfc Enabled:");
                    holder.desc.setText(String.valueOf(((Network) object).isNfcEnabled()));
                break;
            case 8: holder.textView.setText("is Nfc Present:");
                    holder.desc.setText(String.valueOf(((Network) object).isNfcPresent()));
                break;
            case 9: holder.textView.setText("is Wifi Enabled:");
                    holder.desc.setText(String.valueOf(((Network) object).isWifiEnabled()));
                break;
            case 10: holder.textView.setText("is Network Available:");
                     holder.desc.setText(String.valueOf(((Network) object).isNetworkAvailable()));
                break;
            case 11: holder.textView.setText("Network Class:");
                     holder.desc.setText(((Network) object).getNetworkClass());
                break;
            case 12: holder.textView.setText("Network Type:");
                     holder.desc.setText(((Network) object).getNetworkType());
                break;
        }
    }


    private void handleDeviceInfo(CustomViewHolder holder, int position) {
        switch (position) {
            case 0:  holder.textView.setText("Manufacturer:");
                     holder.desc.setText(((Device) object).getManufacturer());
                break;
            case 1: holder.textView.setText("Model:");
                    holder.desc.setText(((Device) object).getModel());
                break;
            case 2: holder.textView.setText("Build VersionCode Name:");
                    holder.desc.setText(((Device) object).getBuildVersionCodeName());
                break;
            case 3: holder.textView.setText("Release Build Version:");
                    holder.desc.setText(((Device) object).getReleaseBuildVersion());
                break;
            case 4: holder.textView.setText("Product:");
                    holder.desc.setText(((Device) object).getProduct());
                break;
            case 5: holder.textView.setText("Fingerprint:");
                    holder.desc.setText(((Device) object).getFingerprint());
                break;
            case 6: holder.textView.setText("Hardware:");
                    holder.desc.setText(((Device) object).getHardware());
                break;
            case 7: holder.textView.setText("Radio Version:");
                    holder.desc.setText(((Device) object).getRadioVersion());
                break;
            case 8: holder.textView.setText("Device:");
                    holder.desc.setText(((Device) object).getDevice());
                break;
            case 9: holder.textView.setText("Board:");
                    holder.desc.setText(((Device) object).getBoard());
                break;
            case 10: holder.textView.setText("Display Version:");
                     holder.desc.setText(((Device) object).getDisplayVersion());
                break;
            case 11: holder.textView.setText("Build Brand:");
                     holder.desc.setText(((Device) object).getBuildBrand());
                break;
            case 12: holder.textView.setText("Build Host:");
                     holder.desc.setText(((Device) object).getBuildHost());
                break;
            case 13: holder.textView.setText("Build Time:");
                     holder.desc.setText(String.valueOf(((Device) object).getBuildTime()));
                break;
            case 14: holder.textView.setText("Build User:");
                     holder.desc.setText(((Device) object).getBuildUser());
                break;
            case 15: holder.textView.setText("Serial:");
                     holder.desc.setText(((Device) object).getSerial());
                break;
            case 16: holder.textView.setText("OS Version:");
                     holder.desc.setText(((Device) object).getOsVersion());
                break;
            case 17: holder.textView.setText("Language:");
                     holder.desc.setText(((Device) object).getLanguage());
                break;
            case 18: holder.textView.setText("SDK Version:");
                     holder.desc.setText(String.valueOf(((Device) object).getSdkVersion()));
                break;
            case 19: holder.textView.setText("Screen Density:");
                     holder.desc.setText(((Device) object).getScreenDensity());
                break;
            case 20: holder.textView.setText("Screen Height:");
                     holder.desc.setText(String.valueOf(((Device) object).getScreenHeight()));
                break;
            case 21: holder.textView.setText("Screen Width:");
                     holder.desc.setText(String.valueOf(((Device) object).getScreenWidth()));
                break;
        }
    }


    private float convertToGb(long valInBytes) {
        return Float.valueOf(String.format("%.2f", (float) valInBytes / (1024 * 1024 * 1024)));
    }
}
