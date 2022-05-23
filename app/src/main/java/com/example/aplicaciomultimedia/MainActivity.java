package com.example.aplicaciomultimedia;

import static com.example.aplicaciomultimedia.classes.MyPermission.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplicaciomultimedia.classes.MyPermission;

public class MainActivity extends AppCompatActivity {

    public static boolean manage_permission_granted = true;
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
                if (manage_permission_granted) {
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    startActivity(intent);
                }
            }
        });
        ivSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manage_permission_granted) {
                    Intent intent = new Intent(MainActivity.this, SoundActivity.class);
                    startActivity(intent);
                }
            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manage_permission_granted) {
                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Primero deves conceder el permiso para acceder a los archivos", Toast.LENGTH_SHORT).show();
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