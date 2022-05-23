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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {

    EditText etText;
    Button btnBuscarArchivo;
    Button btnGuardar;
    Button btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        initComponents();
        initEvents();
    }
    private void initComponents() {
        etText = findViewById(R.id.etText);
        btnBuscarArchivo = findViewById(R.id.btnBuscarArchivo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
    }
    private void initEvents() {
        btnBuscarArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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