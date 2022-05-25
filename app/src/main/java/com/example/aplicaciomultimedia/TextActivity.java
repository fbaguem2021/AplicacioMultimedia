package com.example.aplicaciomultimedia;



import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplicaciomultimedia.classes.MyPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    public static final String DOCUMENTS = "/storage/emulated/0/Documents";

    EditText etText;
    EditText etFileName;
    Button btnGuardar;
    Button btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Intent intent = getIntent();
        boolean createmode = intent.getBooleanExtra(FileListActivity.CREATE_MODE, true);
        String fileName = "";
        if (!createmode) {
            fileName = intent.getStringExtra(FileListActivity.FILE_NAME);
        }

        initComponents(createmode, fileName);
        initEvents(createmode, fileName);
    }
    private void initComponents(boolean createmode, String fileName) {
        etText = findViewById(R.id.etText);
        if (!createmode) {
            leerFichero(DOCUMENTS+"/"+fileName);
        }
        etFileName = findViewById(R.id.etFileName);
        etFileName.setText(fileName);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
    }
    private void initEvents(boolean createmode, String fileName) {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etText.getText().toString();
                if (!etFileName.getText().toString().equals("")) {
                    if (createmode) {
                        saveFile(DOCUMENTS+"/"+etFileName.getText().toString(), text);
                        finish();
                    } else {
                        if (fileName.equals(etFileName.getText().toString())) {
                            saveFile(DOCUMENTS+"/"+fileName, text);
                            Toast.makeText(TextActivity.this, "Archivo guardado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            new File(DOCUMENTS+"/"+fileName).delete();
                            saveFile(DOCUMENTS+"/"+etFileName.getText().toString(), text);
                            Toast.makeText(TextActivity.this, "Archivo guardado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                } else {
                    Toast.makeText(TextActivity.this, "Primero has de intrtoducir un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void leerFichero(String path) {
        try {
            String file = "";
            String s;
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            while ( (s = br.readLine()) != null ) {
                file += s;
            }
            fr.close();

            etText.setText(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveFile(String path, String s) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}