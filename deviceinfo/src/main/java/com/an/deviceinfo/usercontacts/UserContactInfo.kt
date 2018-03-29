package com.an.deviceinfo.usercontacts


import android.Manifest
import android.app.Activity
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract

import com.an.deviceinfo.permission.PermissionUtils

import java.util.ArrayList
import java.util.HashSet

class UserContactInfo(private val activity: Activity) {
    private val permissionUtils: PermissionUtils

    val contacts: List<UserContacts>
        get() {
            if (!permissionUtils.isPermissionGranted(Manifest.permission.BLUETOOTH))
                throw RuntimeException("Access user contacts permission not granted!")

            val contacts = ArrayList<UserContacts>()
            val uniqueValues = HashSet<String>()
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE)

            val people = activity.contentResolver.query(uri, projection, null, null, null)

            val indexName = people!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val indexPhoneType = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)

            people.moveToFirst()
            do {
                if (!uniqueValues.contains(people.getString(indexNumber))) {
                    val contactsModel = UserContacts()
                    contactsModel.displayName = people.getString(indexName)
                    contactsModel.mobileNumber = people.getString(indexNumber)
                    contactsModel.phoneType = people.getString(indexPhoneType)

                    uniqueValues.add(people.getString(indexNumber))
                    contacts.add(contactsModel)
                }
            } while (people.moveToNext())

            return contacts
        }

    init {
        this.permissionUtils = PermissionUtils(activity)
    }
}
