package com.deviceinfosample.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.an.deviceinfo.ads.Ad;
import com.an.deviceinfo.ads.AdInfo;
import com.an.deviceinfo.device.model.App;
import com.an.deviceinfo.device.model.Battery;
import com.an.deviceinfo.device.model.Device;
import com.an.deviceinfo.device.model.Memory;
import com.an.deviceinfo.device.model.Network;
import com.an.deviceinfo.location.LocationInfo;
import com.an.deviceinfo.location.DeviceLocation;
import com.an.deviceinfo.userapps.UserAppInfo;
import com.an.deviceinfo.userapps.UserApps;
import com.an.deviceinfo.usercontacts.UserContactInfo;
import com.an.deviceinfo.usercontacts.UserContacts;
import com.deviceinfosample.R;
import com.deviceinfosample.adapter.CustomListAdapter;

import java.util.List;

public class MainFragment extends Fragment implements AdInfo.AdIdCallback {

    private int position;
    private Activity mActivity;

    private RecyclerView recyclerView;
    private CustomListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static MainFragment newInstance(int pos) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            position = getArguments().getInt("pos");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.smoothScrollToPosition(0);

        initialize();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void initialize() {
     new Handler().post(new Runnable() {
         @Override
         public void run() {
             switch (position) {
                 case 0: //call location
                     LocationInfo locationInfo = new LocationInfo(mActivity);
                     DeviceLocation location = locationInfo.getLocation();
                     adapter = new CustomListAdapter(mActivity, location);
                     break;
                 case 1: //call apps
                     App app = new App(mActivity);
                     adapter = new CustomListAdapter(mActivity, app);
                     break;
                 case 2: //call ads
                     AdInfo adInfo = new AdInfo(mActivity);
                     adInfo.getAndroidAdId(MainFragment.this);
                     break;
                 case 3: //call battery
                     Battery battery = new Battery(mActivity);
                     adapter = new CustomListAdapter(mActivity, battery);
                     break;
                 case 4: //call device
                     Device device = new Device(mActivity);
                     adapter = new CustomListAdapter(mActivity, device);
                     break;
                 case 5: //call memory
                     Memory memory = new Memory(mActivity);
                     adapter = new CustomListAdapter(mActivity, memory);
                     break;
                 case 6: //call network
                     Network network = new Network(mActivity);
                     adapter = new CustomListAdapter(mActivity, network);
                     break;
                 case 7: //call installed apps
                     UserAppInfo userAppInfo = new UserAppInfo(mActivity);
                     List<UserApps> userApps = userAppInfo.getInstalledApps(true);
                     adapter = new CustomListAdapter(mActivity, userApps);
                     break;
                 case 8: //call contacts
                     UserContactInfo userContactInfo = new UserContactInfo(mActivity);
                     List<UserContacts> userContacts = userContactInfo.getContacts();
                     adapter = new CustomListAdapter(mActivity, userContacts);
                     break;
             }

             mActivity.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     if(adapter != null) {
                         recyclerView.setAdapter(adapter);
                     }
                 }
             });
         }
     });
    }

    @Override
    public void onResponse(final Ad ad) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new CustomListAdapter(mActivity, ad);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}
