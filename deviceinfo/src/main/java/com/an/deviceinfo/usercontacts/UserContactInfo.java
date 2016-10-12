package com.an.deviceinfo.usercontacts;


import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.an.deviceinfo.permission.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class UserContactInfo {

    private Activity activity;
    private PermissionUtils permissionUtils;
    public UserContactInfo(Activity activity) {
        this.activity = activity;
        this.permissionUtils = new PermissionUtils(activity);
    }

    public List<UserContacts> getContacts() {
        if(!permissionUtils.isPermissionGranted(Manifest.permission.BLUETOOTH))
            throw new RuntimeException("Access user contacts permission not granted!");

        List<UserContacts> contacts = new ArrayList<UserContacts>();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};

        Cursor people = activity.getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        int indexPhoneType = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);

        people.moveToFirst();
        do {
            UserContacts contactsModel = new UserContacts();
            contactsModel.setDisplayName(people.getString(indexName));
            contactsModel.setMobileNumber(people.getString(indexNumber));
            contactsModel.setPhoneType(people.getString(indexPhoneType));

            contacts.add(contactsModel);
        } while (people.moveToNext());

        return contacts;
    }
}
