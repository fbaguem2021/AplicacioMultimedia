package com.example.aplicaciomultimedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SoundActivity extends AppCompatActivity {
    public static final String CREATE_MODE = "CREATE_MODE";
    public static final String FILE_NAME = "FILE_NAME";

    public static final String MUSIC = "/storage/emulated/0/Music";

    MediaRecorder mediaRecorder ;
    MediaPlayer mediaPlayer;
    ImageView record;
    ImageView stop;
    ImageView play;
    ImageView pause;
    Button btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        Intent intent = getIntent();
        boolean createmode = intent.getBooleanExtra(CREATE_MODE, true);
        String fileName = "";
        if (!createmode) {
            fileName = intent.getStringExtra(FILE_NAME);
        }

        initComponents();
        initEvents(createmode, fileName);
    }
    private void initComponents(){
        record = findViewById(R.id.ivRecord);
        stop = findViewById(R.id.ivStop);
        play = findViewById(R.id.ivPlay);
        pause = findViewById(R.id.ivPause);
        btnClose = findViewById(R.id.btnClose);
        pause.setEnabled(false);
    }
    private void initEvents(boolean createmode, String filename) {
        if (createmode) {
            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        play.setEnabled(false);
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        mediaRecorder.setOutputFile(getRecordingFilePath());
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        record.setVisibility(View.INVISIBLE);
                        stop.setVisibility(View.VISIBLE);
                        Toast.makeText(SoundActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(SoundActivity.this, "error", Toast.LENGTH_SHORT).show();
                        Log.d("\n", "error");
                        e.printStackTrace();
                        Log.d("\n", "error");
                    }
                }
            });
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    Toast.makeText(SoundActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();

                    stop.setVisibility(View.INVISIBLE);
                    record.setVisibility(View.VISIBLE);
                }
            });
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer = new MediaPlayer();
                    if (createmode) {
                        mediaPlayer.setDataSource(getRecordingFilePath());
                    } else {
                        mediaPlayer.setDataSource(filename);
                    }
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(SoundActivity.this, "Audio started", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                record.setEnabled(false);
                play.setEnabled(false);
                pause.setEnabled(true);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                record.setEnabled(true);
                play.setEnabled(true);
                pause.setEnabled(false);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder = null;
                } else if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
                finish();
            }
        });
    }
    private String getRecordingFilePath() {
        File file = new File("audio_" + (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale
                .getDefault())).format(new Date()) + ".mp3");
        return file.getPath();

    }
    private boolean isMicrophonePresent() {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        } else {
            return false;
        }
    }
}