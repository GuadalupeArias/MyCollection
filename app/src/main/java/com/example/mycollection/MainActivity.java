package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mycollection.servicies.UsuarioService;

import entidades.Usuario;

public class MainActivity extends AppCompatActivity {
    ImageButton btnRegistrarse;
    ImageButton btnIniciarSesion;
    EditText etUsuario;
    EditText etPass;
    UsuarioService usuarioService = new UsuarioService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,null, 1);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        etUsuario = findViewById(R.id.ETUsuario);
        etPass = findViewById(R.id.ETPassword);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO}, 0);
        }

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i1);
                finish();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=etUsuario.getText().toString();
                String p=etPass.getText().toString();
                if (u.equals("")&&p.equals(""))
                    Toast.makeText(MainActivity.this, "ERROR: campos vacios", Toast.LENGTH_SHORT).show();
                else {
                    Usuario usuario =usuarioService.getUserByUserAndPass(u,p, MainActivity.this);
                    if (usuario==null){
                        Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent i2 = new Intent(MainActivity.this, Inicio.class);
                        i2.putExtra("Id", usuario.getId());
                        ((EditText) findViewById(R.id.ETUsuario)).setText("");
                        ((EditText) findViewById(R.id.ETPassword)).setText("");
                        startActivity(i2);

                    }



                }
            }
        });

    }


}