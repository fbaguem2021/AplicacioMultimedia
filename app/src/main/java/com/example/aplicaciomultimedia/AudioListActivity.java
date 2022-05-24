package com.example.aplicaciomultimedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class AudioListActivity extends AppCompatActivity {
    public static final String CREATE_MODE = "CREATE_MODE";
    public static final String FILE_NAME = "FILE_NAME";

    public static final String DOCUMENTS = "/storage/emulated/0/Music";
    ListView lstFiles;
    Button btnNuevoFichero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        initElements();
    }
    public void initElements() {
        lstFiles = findViewById(R.id.lstAudios);
        btnNuevoFichero = findViewById(R.id.btnNuevoAudio);
        final ArrayList<String> files = getFiles(DOCUMENTS);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, files);
        lstFiles.setAdapter(adapter);

        lstFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AudioListActivity.this, SoundActivity.class);
                intent.putExtra(CREATE_MODE, false);
                intent.putExtra(FILE_NAME, files.get(position));
                startActivity(intent);
            }
        });
        btnNuevoFichero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioListActivity.this, SoundActivity.class);
                intent.putExtra(CREATE_MODE, true);
                startActivity(intent);
            }
        });
    }
    public ArrayList<String> getFiles(@NonNull String path) {
        ArrayList<String> files = new ArrayList<>();
        for (File f : new File(path+"/").listFiles()) {
            if (f.isFile()) {
                files.add(f.getName());
            }
        }
        return files;
    }
    @Override
    protected void onResume() {
        super.onResume();
        initElements();
    }
}