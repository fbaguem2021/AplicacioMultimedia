package com.example.aplicaciomultimedia.classes;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyPermission {
    public static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    public static void checkPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT < 23 ) {
            if (checkPermissions(activity, PERMISSIONS)) {
                start(activity);
            }
        } else {
            requestPermissions(activity, PERMISSIONS);
        }
    }
    public static boolean checkPermissions(Activity activity, String[] PERMISSIONS) {
        boolean checked = true;
        int i = 0;
        while ( i < PERMISSIONS.length && checked) {
            if (ContextCompat.checkSelfPermission(activity, PERMISSIONS[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                checked = false;
            }
            i++;
        }
        return checked;
    }
    private static void requestPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 1);
    }
    public static void start(Activity activity) {
        Toast.makeText(activity, "Permissions Granted", Toast.LENGTH_SHORT).show();
    }
}
