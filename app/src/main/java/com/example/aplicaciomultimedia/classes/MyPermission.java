package com.example.aplicaciomultimedia.classes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.aplicaciomultimedia.MainActivity;

public class MyPermission {
    public static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
           //,Manifest.permission.MANAGE_EXTERNAL_STORAGE
    };
    public static final int REQ_CODE_GENERAL = 1;
    public static final int REQ_CODE_MANAGE = 2;

    public static void checkPermissions(MainActivity activity) {
        if (Build.VERSION.SDK_INT >= 23 ) {
            if (checkPermissions(activity, PERMISSIONS)) {
                activity.checkAllFilesPermission();
            }
        } else {
            start(activity);
        }
    }
    public static void requestPermissions(MainActivity activity) {
        if (Build.VERSION.SDK_INT >23 ) {
            if (checkPermissions(activity, PERMISSIONS)) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS, REQ_CODE_GENERAL);
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    activity.checkAllFilesPermission();
                } else {
                    MainActivity.manage_permission_granted = true;
                }
            } else {
                activity.checkAllFilesPermission();
            }
        } else {
            activity.checkAllFilesPermission();
        }
    }
    public static boolean checkPermissions(Activity activity, String[] PERMISSIONS) {
        boolean checked = true;
        int i = 0;
        while ( i < PERMISSIONS.length && checked) {
            if (ContextCompat.checkSelfPermission(activity, PERMISSIONS[i]) ==
                    PackageManager.PERMISSION_GRANTED) {
                checked = false;
            }
            i++;
        }
        return checked;
    }
//    private static void requestPermissions(Activity activity, String[] permissions) {
//        ActivityCompat.requestPermissions(activity, permissions, REQ_CODE_GENERAL);
//    }
    public static void start(Activity activity) {
        Toast.makeText(activity, "Permissions Granted", Toast.LENGTH_SHORT).show();
    }

}
