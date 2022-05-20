package com.example.aplicaciomultimedia;

import static com.example.aplicaciomultimedia.classes.MyPermission.checkPermissions;

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
}