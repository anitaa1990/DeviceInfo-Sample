package com.an.deviceinfo.permission

import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log


class PermissionManager : DialogInterface.OnClickListener {

    private val MY_PERMISSIONS = 1012

    private val PERMISSIONS_TAG = "Permissions"

    private val DEFAULT_DENY_DIALOG_TITLE = "Permission Required"
    private val DEFAULT_DENY_DIALOG_POS_BTN = "GO TO SETTINGS"
    private val DEFAULT_DENY_DIALOG_NEG_BTN = "CANCEL"

    private var activity: Activity? = null
    private var fragment: Fragment? = null

    private var permissionUtils: PermissionUtils? = null
    private var permissionCallback: PermissionCallback? = null

    private var permission: String? = null
    private var showDenyDialog = true
    private var showRationale = true
    private var denyDialogText: String? = null
    private var denyDialogTitle = DEFAULT_DENY_DIALOG_TITLE
    private var denyPosBtnTxt = DEFAULT_DENY_DIALOG_POS_BTN
    private var denyNegBtnTxt = DEFAULT_DENY_DIALOG_NEG_BTN
    private var showNegBtn = true
    private var isCancellable = true


    private var alertDialog: AlertDialog? = null

    constructor(fragment: Fragment) {
        this.fragment = fragment
        this.activity = fragment.activity
        permissionUtils = PermissionUtils(activity!!)
    }

    constructor(activity: Activity) {
        this.activity = activity
        permissionUtils = PermissionUtils(activity)
    }


    /**
     * This is the method to be called to display permission dialog
     * pass the permission string along with this method
     */
    fun showPermissionDialog(permission: String): PermissionManager {
        this.permission = permission
        return this
    }

    /**
     * This should be called in case you want to display
     * a custom dialog explaining why you require this
     * permission.
     * This will be called once the user has denied the
     * permission and checked the "never show again" button
     */
    fun withDenyDialogEnabled(showDenyDialog: Boolean): PermissionManager {
        this.showDenyDialog = showDenyDialog
        return this
    }

    /**
     * This should be called in case you want to display
     * android dialog to users if they have not checked the
     * "never show again" button
     * This will be called once the user has denied the
     * permission the first time
     */
    fun withRationaleEnabled(showRationale: Boolean): PermissionManager {
        this.showRationale = showRationale
        return this
    }

    /**
     * This will display the description text explaining to the users
     * why we need this permission
     */
    fun withDenyDialogMsg(denyDialogText: String): PermissionManager {
        this.denyDialogText = denyDialogText
        return this
    }

    /**
     * This is an option parameter to display the title for the
     * custom dialog. By default, it will be "Permission required"
     */
    fun withDenyDialogTitle(denyDialogTitle: String): PermissionManager {
        this.denyDialogTitle = denyDialogTitle
        return this
    }


    /**
     * This is an option parameter to display the positive button text
     * for the custom dialog. By default, it will be "GO TO SETTINGS"
     * This will redirect the users to the settings screen once clicked
     */
    fun withDenyDialogPosBtnText(denyPosBtnTxt: String): PermissionManager {
        this.denyPosBtnTxt = denyPosBtnTxt
        return this
    }

    /**
     * This is an option parameter to display the negative button text
     * for the custom dialog. By default, it will be "Cancel"
     */
    fun withDenyDialogNegBtnText(denyNegBtnTxt: String): PermissionManager {
        this.denyNegBtnTxt = denyNegBtnTxt
        return this
    }

    /**
     * This is an option parameter to show/hide the negative dialog
     * button. This is to make sure that the user has no choice but
     * to grant the permission. By default this flag will be false.
     */
    fun withDenyDialogNegBtn(showNegBtn: Boolean): PermissionManager {
        this.showNegBtn = showNegBtn
        return this
    }

    /**
     * This is an option parameter to provide users with an option
     * to cancel the permission dialog. If this is false the user
     * has no choice but to grant the permission.
     * By default this flag will be true.
     */
    fun isDialogCancellable(isCancellable: Boolean): PermissionManager {
        this.isCancellable = isCancellable
        return this
    }

    /**
     * This is an option parameter to receive a callback for the
     * permission
     */
    fun withCallback(permissionCallback: PermissionCallback): PermissionManager {
        this.permissionCallback = permissionCallback
        return this
    }

    /**
     * Build method will be invoked only when the permission string is valid
     * and if the showDenyDialog flag is enabled, you need to pass a dialog text
     * in order to build the permissions dialog
     */
    @Synchronized
    fun build() {
        if (permission == null) {
            throw RuntimeException("You need to set a permission before calling Build method!")
        }

        if (permissionCallback == null) {
            throw RuntimeException("You need to set a permissionCallback before calling Build method!")
        }

        if (showDenyDialog && denyDialogText == null) {
            throw RuntimeException("You need to set a deny Dialog description message before calling Build method!")
        }

        /**
         * Check if permission is already granted.
         * If so, do not ask again
         */
        if (permissionUtils!!.isPermissionGranted(permission!!)) {
            Log.d(PERMISSIONS_TAG, String.format("%s permission already granted!", permission))
            return
        }
        //
        //        /**
        //         * User has denied permission and has checked the never
        //         * show again button.
        //         * Custom deny dialog is displayed
        //         * */
        //        if(showDenyDialog && !activity.shouldShowRequestPermissionRationale(permission) && showRationale) {
        //            displayDenyDialog();
        //            return;
        //        }

        askPermissionDialog()
    }

    @Synchronized private fun askPermissionDialog() {
        if (hasStarted) return
        hasStarted = true

        /**
         * onPermissionResult will only be called inside a fragment
         * if the permission is asked in this way.
         * We need to call fragment.requestPermissions when asking
         * permission in a fragment
         */
        if (fragment != null) {
            fragment!!.requestPermissions(arrayOf<String>(permission!!), MY_PERMISSIONS)
            return
        }

        /**
         * If permission is not called inside a fragment and called only
         * in the activity, then the below code is executed
         */
        ActivityCompat.requestPermissions(activity!!, arrayOf<String>(permission!!), MY_PERMISSIONS)
    }


    @Synchronized
    fun handleResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (permissions.size == 0) return
        val permission = permissions[0]
        when (requestCode) {
            MY_PERMISSIONS -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /**
                 * Permission is granted
                 */
                hasStarted = false
                if (permissionCallback != null) permissionCallback!!.onPermissionGranted(permissions, grantResults)

            } else if (activity!!.shouldShowRequestPermissionRationale(permission) && showRationale) {
                hasStarted = false
                /**
                 * Show permission dialog again but only if "deny show again" button is not checked by user
                 * and if the user has specifically allowed to display the permission
                 */
                askPermissionDialog()

            } else if (showDenyDialog) {
                /**
                 * Show custom permission dialog explaining to the user why the app requires the permission
                 * This should only be displayed if the app developer has specified it
                 * It will be displayed by default and it will open the settings page by default
                 */
                displayDenyDialog()

            } else {
                /**
                 * User has denied the permission even after explanation.
                 * Denied callback is invoked
                 */
                if (permissionCallback != null) permissionCallback!!.onPermissionDismissed(permission)
            }
        }
    }

    @Synchronized private fun displayDenyDialog() {
        val alertDialogBuilder = AlertDialog.Builder(activity!!)
                /**
                 * dialog Title
                 */
                .setTitle(denyDialogTitle)
                /**
                 * dialog description
                 * *  */
                .setMessage(denyDialogText)
                /**
                 * dialog should be cancellable
                 * *  */
                .setCancelable(isCancellable)
                /**
                 * dialog Postive Button text
                 * *  */
                .setPositiveButton(denyPosBtnTxt, this)

        /**
         * dialog Negative Button button
         * By default this will be displayed to the user
         * *  */
        if (showNegBtn) {
            alertDialogBuilder.setNegativeButton(denyNegBtnTxt, this)
        }

        /**
         * create alert dialog
         * *  */
        alertDialog = alertDialogBuilder.show()
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        hasStarted = false
        if (alertDialog != null && alertDialog!!.isShowing)
            alertDialog!!.dismiss()

        if (which == DialogInterface.BUTTON_POSITIVE) {
            permissionCallback!!.onPositiveButtonClicked(dialog, which)
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            permissionCallback!!.onNegativeButtonClicked(dialog, which)
        }
    }


    /**
     * Custom callback interface users can invoke to receive status
     * about the permissions.
     * This is an optional parameter
     */
    interface PermissionCallback {
        fun onPermissionGranted(permissions: Array<String>, grantResults: IntArray)
        fun onPermissionDismissed(permission: String)
        fun onPositiveButtonClicked(dialog: DialogInterface, which: Int)
        fun onNegativeButtonClicked(dialog: DialogInterface, which: Int)
    }

    companion object {
        private var hasStarted = false
    }
}