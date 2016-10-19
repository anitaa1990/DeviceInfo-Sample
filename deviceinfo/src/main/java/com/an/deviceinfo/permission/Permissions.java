package com.an.deviceinfo.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

public class Permissions extends Activity {

    private final int MY_PERMISSIONS = 1012;

    private final String PERMISSIONS_TAG = "Permissions";

    private final String DEFAULT_DENY_DIALOG_TITLE = "Permission Required";
    private final String DEFAULT_DENY_DIALOG_POS_BTN = "GO TO SETTINGS";
    private final String DEFAULT_DENY_DIALOG_NEG_BTN = "CANCEL";

    private Context context;
    private PermissionCallback permissionCallback;
    public Permissions(Context context) {
        this.context = context;
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
    public Permissions showPermissionDialog(String permission) {
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
    public Permissions withDenyDialogEnabled(boolean showDenyDialog) {
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
    public Permissions withRationaleEnabled(boolean showRationale) {
        this.showRationale = showRationale;
        return this;
    }

    /**
     * This will display the description text explaining to the users
     * why we need this permission
     * */
    public Permissions withDenyDialogMsg(String denyDialogText) {
        this.denyDialogText = denyDialogText;
        return this;
    }

    /**
     * This is an option parameter to display the title for the
     * custom dialog. By default, it will be "Permission required"
     * */
    public Permissions withDenyDialogTitle(String denyDialogTitle) {
        this.denyDialogTitle = denyDialogTitle;
        return this;
    }


    /**
     * This is an option parameter to display the positive button text
     * for the custom dialog. By default, it will be "GO TO SETTINGS"
     * This will redirect the users to the settings screen once clicked
     * */
    public Permissions withDenyDialogPosBtnText(String denyPosBtnTxt) {
        this.denyPosBtnTxt = denyPosBtnTxt;
        return this;
    }

    /**
     * This is an option parameter to display the negative button text
     * for the custom dialog. By default, it will be "Cancel"
     * */
    public Permissions withDenyDialogNegBtnText(String denyNegBtnTxt) {
        this.denyNegBtnTxt = denyNegBtnTxt;
        return this;
    }

    /**
     * This is an option parameter to show/hide the negative dialog
     * button. This is to make sure that the user has no choice but
     * to grant the permission. By default this flag will be false.
     * */
    public Permissions withDenyDialogNegBtn(boolean showNegBtn) {
        this.showNegBtn = showNegBtn;
        return this;
    }

    /**
     * This is an option parameter to provide users with an option
     * to cancel the permission dialog. If this is false the user
     * has no choice but to grant the permission.
     * By default this flag will be true.
     * */
    public Permissions isDialogCancellable(boolean isCancellable) {
        this.isCancellable = isCancellable;
        return this;
    }

    /**
     * This is an option parameter to receive a callback for the
     * permission
     * */
    public Permissions withCallback(PermissionCallback permissionCallback) {
        this.permissionCallback = permissionCallback;
        return this;
    }

    /**
     * Build method will be invoked only when the permission string is valid
     * and if the showDenyDialog flag is enabled, you need to pass a dialog text
     * in order to build the permissions dialog
     * */
    public void build() {
        if(permission == null) {
            throw new RuntimeException("You need to set a permission before calling Build method!");
        }
        if(showDenyDialog && denyDialogText == null) {
            throw new RuntimeException("You need to set a deny Dialog description message before calling Build method!");
        }
        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, MY_PERMISSIONS);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String permission = permissions[0];
        switch (requestCode) {
            case MY_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**
                     * Permission is granted
                     * */
                    if(permissionCallback != null) permissionCallback.onPermissionGranted(permissions, grantResults);

                } else if(!shouldShowRequestPermissionRationale(permission) && showRationale) {
                    /**
                     * Show permission dialog again but only if "deny show again" button is not checked by user
                     * and if the user has specifically allowed to display the permission
                     * */
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, MY_PERMISSIONS);

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

    private void displayDenyDialog() {
        AlertDialog alertDialog = null;
        final AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this)
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
                   * dialog Positive button text
                   * * */
                .setPositiveButton(denyPosBtnTxt, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /**
                         * Open the app settings screen
                         * * */
                        PermissionUtils permissionUtils = new PermissionUtils(context);
                        permissionUtils.openAppSettings();
                    }
                });

            /**
             * Will be displayed by default
             * * */
        if(showNegBtn) {
            final AlertDialog finalAlertDialog = alertDialog;
            alertDialogBuilder.setNegativeButton(denyNegBtnTxt, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   if(finalAlertDialog != null && finalAlertDialog.isShowing()) {
                       finalAlertDialog.dismiss();
                   }
                   if(permissionCallback != null) permissionCallback.onPermissionDismissed(permission);
               }
           });
        }
        alertDialog = alertDialogBuilder.show();
    }


    /**
     * Custom callback interface users can invoke to receive status
     * about the permissions.
     * This is an optional parameter
     * */
    public interface PermissionCallback {
        void onPermissionGranted(String[] permissions, int[] grantResults);
        void onPermissionDismissed(String permission);
    }
}
