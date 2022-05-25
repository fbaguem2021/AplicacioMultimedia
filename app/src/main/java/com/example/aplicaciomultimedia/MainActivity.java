package com.example.aplicaciomultimedia;

import static com.example.aplicaciomultimedia.classes.MyPermission.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplicaciomultimedia.classes.MyPermission;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static int RECORD_REQUEST = 0;
    public static boolean manage_permission_granted = true;
    public static final int SOUND_CODE = 1;
    public static final int VIDEO_CODE = 2;
    public static final int IMAGE_CODE = 3;
    ImageView ivText;
    ImageView ivImage;
    ImageView ivSound;
    ImageView ivVideo;
    Button btnPermissions;
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
        ivText  = findViewById(R.id.ivText);
        ivImage = findViewById(R.id.ivImage);
        ivSound = findViewById(R.id.ivSound);
        ivVideo = findViewById(R.id.ivVideo);

    }
    private void initEvents() {
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
//                if (manage_permission_granted) {
//                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
//                    startActivity(intent);
//                }
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
//                if (manage_permission_granted) {
//                    Intent intent = new Intent(MainActivity.this, AudioListActivity.class);
//                    startActivity(intent);
//                }
            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
//                startActivity(intent);
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
        int i = 0;
        String p = "";
        if (requestCode == REQ_CODE_MANAGE &&
            grantResults.length > 0 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
            grantResults[2] == PackageManager.PERMISSION_GRANTED &&
            grantResults[3] == PackageManager.PERMISSION_GRANTED &&
            grantResults[4] == PackageManager.PERMISSION_GRANTED &&
            grantResults[5] == PackageManager.PERMISSION_GRANTED)
        {
            checkAllFilesPermission();
        }

//        while (i < permissions.length && checked) {
//            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                checked = false;
//                p+=permissions[i]+",\n";
//            }
//
//            i++;
//        }
//
//        if (checked) {
//            MyPermission.start(this);
//        } else {
//            Toast.makeText(this, "Permissions:\n"+p+" not Granted", Toast.LENGTH_SHORT).show();
//        }
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