package com.example.aplicaciomultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FileListActivity extends AppCompatActivity {

    public static final String DOCUMENTS = "/storage/emulated/0/Documents";
    ListView lstFiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        lstFiles = findViewById(R.id.lstFiles);

    }
    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<>();
        for (File f : new File(path).listFiles()) {
            if (f.isFile()) {
                files.add(f.getName());
            }
        }
        return files;
    }
}