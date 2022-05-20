package com.example.aplicaciomultimedia;

import static com.example.aplicaciomultimedia.classes.MyPermission.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplicaciomultimedia.classes.MyPermission;

public class MainActivity extends AppCompatActivity {

    ImageView ivText;
    ImageView ivImage;
    ImageView ivSound;
    ImageView ivVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions((Activity) this);

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
                Intent intent = new Intent(MainActivity.this, TextActivity.class);
                startActivity(intent);
            }
        });
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });
        ivSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SoundActivity.class);
                startActivity(intent);
            }
        });
        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean checked = true;
        int i = 0;
        while (i < permissions.length && checked) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                checked = false;
            }

            i++;
        }

        if (checked) {
            MyPermission.start(this);
        } else {
            Toast.makeText(this, "Permissions not Granted", Toast.LENGTH_SHORT).show();
        }
    }
}