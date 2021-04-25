package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycollection.servicies.UsuarioService;

public class MainActivity extends AppCompatActivity {
    Button btnRegistrarse;
    Button btnIniciarSesion;
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

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(i1);
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u=etUsuario.getText().toString();
                String p=etPass.getText().toString();
                if (u.equals("")&&p.equals(""))
                    Toast.makeText(MainActivity.this, "ERROR: campos vacios", Toast.LENGTH_SHORT).show();
                else if (usuarioService.login(u,p,MainActivity.this)==1){

                    Intent i2 = new Intent(MainActivity.this, Inicio.class);
                    startActivity(i2);
                    ((EditText) findViewById(R.id.ETUsuario)).setText("");
                    ((EditText) findViewById(R.id.ETPassword)).setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}