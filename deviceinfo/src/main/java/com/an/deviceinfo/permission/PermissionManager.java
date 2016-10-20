package com.an.deviceinfo.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;


public class PermissionManager implements DialogInterface.OnClickListener {
    private static boolean hasStarted = false;

    private final int MY_PERMISSIONS = 1012;

    private final String PERMISSIONS_TAG = "Permissions";

    private final String DEFAULT_DENY_DIALOG_TITLE = "Permission Required";
    private final String DEFAULT_DENY_DIALOG_POS_BTN = "GO TO SETTINGS";
    private final String DEFAULT_DENY_DIALOG_NEG_BTN = "CANCEL";

    private Activity activity;
    private Fragment fragment;

    private PermissionUtils permissionUtils;
    private PermissionCallback permissionCallback;

    public PermissionManager(Fragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        permissionUtils = new PermissionUtils(activity);
    }

    public PermissionManager(Activity activity) {
        this.activity = activity;
        permissionUtils = new PermissionUtils(activity);
    }

    private String permission;
    private boolean showDenyDialog = true;
    private boolean showRationale = true;
    private String denyDialogText;
    private String denyDialogTitle = DEFAULT_DENY_DIALOG_TITLE;
    private String denyPosBtnTxt = DEFAULT_DENY_DIALOG_POS_BTN;
    private String denyNegBtnTxt = DEFAULT_DENY_DIALOG_NEG_BTN;
    private boolean showNegBtn = true;
    private boolean isCancellable = true;


    /**
     * This is the method to be called to display permission dialog
     * pass the permission string along with this method
     * */
    public PermissionManager showPermissionDialog(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * This should be called in case you want to display
     * a custom dialog explaining why you require this
     * permission.
     * This will be called once the user has denied the
     * permission and checked the "never show again" button
     * */
    public PermissionManager withDenyDialogEnabled(boolean showDenyDialog) {
        this.showDenyDialog = showDenyDialog;
        return this;
    }

    /**
     * This should be called in case you want to display
     * android dialog to users if they have not checked the
     * "never show again" button
     * This will be called once the user has denied the
     * permission the first time
     * */
    public PermissionManager withRationaleEnabled(boolean showRationale) {
        this.showRationale = showRationale;
        return this;
    }

    /**
     * This will display the description text explaining to the users
     * why we need this permission
     * */
    public PermissionManager withDenyDialogMsg(String denyDialogText) {
        this.denyDialogText = denyDialogText;
        return this;
    }

    /**
     * This is an option parameter to display the title for the
     * custom dialog. By default, it will be "Permission required"
     * */
    public PermissionManager withDenyDialogTitle(String denyDialogTitle) {
        this.denyDialogTitle = denyDialogTitle;
        return this;
    }


    /**
     * This is an option parameter to display the positive button text
     * for the custom dialog. By default, it will be "GO TO SETTINGS"
     * This will redirect the users to the settings screen once clicked
     * */
    public PermissionManager withDenyDialogPosBtnText(String denyPosBtnTxt) {
        this.denyPosBtnTxt = denyPosBtnTxt;
        return this;
    }

    /**
     * This is an option parameter to display the negative button text
     * for the custom dialog. By default, it will be "Cancel"
     * */
    public PermissionManager withDenyDialogNegBtnText(String denyNegBtnTxt) {
        this.denyNegBtnTxt = denyNegBtnTxt;
        return this;
    }

    /**
     * This is an option parameter to show/hide the negative dialog
     * button. This is to make sure that the user has no choice but
     * to grant the permission. By default this flag will be false.
     * */
    public PermissionManager withDenyDialogNegBtn(boolean showNegBtn) {
        this.showNegBtn = showNegBtn;
        return this;
    }

    /**
     * This is an option parameter to provide users with an option
     * to cancel the permission dialog. If this is false the user
     * has no choice but to grant the permission.
     * By default this flag will be true.
     * */
    public PermissionManager isDialogCancellable(boolean isCancellable) {
        this.isCancellable = isCancellable;
        return this;
    }

    /**
     * This is an option parameter to receive a callback for the
     * permission
     * */
    public PermissionManager withCallback(PermissionCallback permissionCallback) {
        this.permissionCallback = permissionCallback;
        return this;
    }

    /**
     * Build method will be invoked only when the permission string is valid
     * and if the showDenyDialog flag is enabled, you need to pass a dialog text
     * in order to build the permissions dialog
     * */
    public synchronized void build() {
        if(permission == null) {
            throw new RuntimeException("You need to set a permission before calling Build method!");
        }

        if(permissionCallback == null) {
            throw new RuntimeException("You need to set a permissionCallback before calling Build method!");
        }

        if(showDenyDialog && denyDialogText == null) {
            throw new RuntimeException("You need to set a deny Dialog description message before calling Build method!");
        }

        /**
         * Check if permission is already granted.
         * If so, do not ask again
         * */
        if(permissionUtils.isPermissionGranted(permission)) {
            Log.d(PERMISSIONS_TAG, String.format("%s permission already granted!", permission));
            return;
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

        askPermissionDialog();
    }

    private synchronized void askPermissionDialog() {
        if(hasStarted) return;
        hasStarted = true;

        /**
         * onPermissionResult will only be called inside a fragment
         * if the permission is asked in this way.
         * We need to call fragment.requestPermissions when asking
         * permission in a fragment
         * */
        if(fragment != null) {
            fragment.requestPermissions(new String[]{permission}, MY_PERMISSIONS);
            return;
        }

        /**
         * If permission is not called inside a fragment and called only
         * in the activity, then the below code is executed
         * */
        ActivityCompat.requestPermissions(activity, new String[]{permission}, MY_PERMISSIONS);
    }


    public synchronized void handleResult(int requestCode, String[] permissions, int[] grantResults) {
        if(permissions.length == 0) return;
        String permission = permissions[0];
        switch (requestCode) {
            case MY_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**
                     * Permission is granted
                     * */
                    hasStarted = false;
                    if(permissionCallback != null) permissionCallback.onPermissionGranted(permissions, grantResults);

                } else if(activity.shouldShowRequestPermissionRationale(permission) && showRationale) {
                    hasStarted = false;
                    /**
                     * Show permission dialog again but only if "deny show again" button is not checked by user
                     * and if the user has specifically allowed to display the permission
                     * */
                    askPermissionDialog();

                } else if(showDenyDialog) {
                    /**
                     * Show custom permission dialog explaining to the user why the app requires the permission
                     * This should only be displayed if the app developer has specified it
                     * It will be displayed by default and it will open the settings page by default
                     * */
                    displayDenyDialog();

                } else {
                    /**
                     * User has denied the permission even after explanation.
                     * Denied callback is invoked
                     * */
                    if(permissionCallback != null) permissionCallback.onPermissionDismissed(permission);
                }
                break;
        }
    }


    private AlertDialog alertDialog;
    private synchronized void displayDenyDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(activity)
                        /**
                         * dialog Title
                         * */
                        .setTitle(denyDialogTitle)
                        /**
                         * dialog description
                         * * */
                        .setMessage(denyDialogText)
                        /**
                         * dialog should be cancellable
                         * * */
                        .setCancelable(isCancellable)
                        /**
                         * dialog Postive Button text
                         * * */
                        .setPositiveButton(denyPosBtnTxt, this);

        /**
         * dialog Negative Button button
         * By default this will be displayed to the user
         * * */
        if (showNegBtn) {
            alertDialogBuilder.setNegativeButton(denyNegBtnTxt, this);
        }

        /**
         * create alert dialog
         * * */
        alertDialog = alertDialogBuilder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        hasStarted = false;
        if(alertDialog != null && alertDialog.isShowing())
            alertDialog.dismiss();

        if (which == DialogInterface.BUTTON_POSITIVE) {
            permissionCallback.onPositiveButtonClicked(dialog, which);
        } else if(which == DialogInterface.BUTTON_NEGATIVE) {
            permissionCallback.onNegativeButtonClicked(dialog, which);
        }
    }


    /**
     * Custom callback interface users can invoke to receive status
     * about the permissions.
     * This is an optional parameter
     * */
    public interface PermissionCallback {
        void onPermissionGranted(String[] permissions, int[] grantResults);
        void onPermissionDismissed(String permission);
        void onPositiveButtonClicked(DialogInterface dialog, int which);
        void onNegativeButtonClicked(DialogInterface dialog, int which);
    }
}
