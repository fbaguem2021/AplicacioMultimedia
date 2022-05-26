package com.example.aplicaciomultimedia;

import static com.example.aplicaciomultimedia.classes.MyPermission.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplicaciomultimedia.classes.MyAddress;
import com.example.aplicaciomultimedia.classes.MyPermission;

public class MainActivity extends AppCompatActivity {

    MyAddress myAddress;
    public static final String GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps";
    public static boolean manage_permission_granted = true;
    public static final int SOUND_CODE = 1;
    public static final int VIDEO_CODE = 2;
    public static final int IMAGE_CODE = 3;
    boolean locationEnabled = false;
    ImageView ivInternet;
    ImageView ivMaps;
    ImageView ivText;
    ImageView ivImage;
    ImageView ivSound;
    ImageView ivVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPermission.requestPermissions(this);

        Toast.makeText(this, ""+Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
        initComponents();
        initEvents();
    }
    private void initComponents(){
        ivInternet = findViewById(R.id.ivInternet);
        ivMaps  = findViewById(R.id.ivMaps);
        ivText  = findViewById(R.id.ivText);
        ivImage = findViewById(R.id.ivImage);
        ivSound = findViewById(R.id.ivSound);
        ivVideo = findViewById(R.id.ivVideo);
    }
    private void initEvents() {
        ivInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAddress address = new MyAddress(MainActivity.this).generateLocation().generateUri();
                Intent intent = new Intent(Intent.ACTION_VIEW, address.getUri());
                intent.setPackage(GOOGLE_MAPS_PACKAGE);
                Toast.makeText(MainActivity.this, ""+(address.location == null), Toast.LENGTH_SHORT).show();
                //startActivity(intent);
            }
        });
        ivText .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manage_permission_granted) {
                    Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                    startActivity(intent);
                }
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CODE);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(v.getContext(), "Este dispositivo no permite la captura de imagenes", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });
        ivSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                    startActivityForResult(intent, SOUND_CODE);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(v.getContext(), "Este dispositivo no permite la gravación de audio", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, VIDEO_CODE);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(v.getContext(), "Este dispositivo no permite la captura de video", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });
    }

    public void checkAllFilesPermission() {
        if (Build.VERSION.SDK_INT >= 30) {

            if (Environment.isExternalStorageManager()) {
                start(this);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, REQ_CODE_MANAGE);
            }

        } else {
            MyPermission.start(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean checked = true;

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(MainActivity.this, ""+i, Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQ_CODE_MANAGE &&
            grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
            grantResults[2] == PackageManager.PERMISSION_GRANTED &&
            grantResults[3] == PackageManager.PERMISSION_GRANTED &&
            grantResults[4] == PackageManager.PERMISSION_GRANTED &&
            grantResults[5] == PackageManager.PERMISSION_GRANTED &&
            grantResults[6] == PackageManager.PERMISSION_GRANTED)
        {
            checkAllFilesPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Build.VERSION.SDK_INT >= 30) {
            if (Environment.isExternalStorageManager()) {
                start(this);
                manage_permission_granted = true;
            } else {
                Toast.makeText(this, "S'ha de donar permissos a l'aplicacio per accedir als arxius", Toast.LENGTH_SHORT).show();
                manage_permission_granted = false;

            }
        }
    }
}