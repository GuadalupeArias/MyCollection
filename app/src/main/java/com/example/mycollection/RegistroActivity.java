package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycollection.servicies.UsuarioService;

import entidades.Usuario;
import utilidades.Utilidades;

public class RegistroActivity extends AppCompatActivity {
    Button btnRegistrarUser;
    EditText campoNombre, campoUsuario, campoPassword, campoMail, campoContrasenia;
    UsuarioService usuarioService = new UsuarioService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        campoNombre= (EditText) findViewById(R.id.ETNombre);
        campoUsuario= (EditText) findViewById(R.id.ETUsuario2);
        campoContrasenia=(EditText) findViewById(R.id.ETRepetirPass);
        campoPassword= (EditText) findViewById(R.id.ETPassword2);
        campoMail= (EditText) findViewById(R.id.ETEmail);


        btnRegistrarUser=findViewById(R.id.btnRegistrar);
        btnRegistrarUser.setOnClickListener(new View.OnClickListener() {
            @Override

            //hacer la comprobacion de que el usuario no exista antes de que lo guarde.-
            public void onClick(View v) {

                registrarUsuarios();

            }
        });
    }

    private void registrarUsuarios(){
        Usuario usuario =new Usuario(campoNombre.getText().toString(), campoUsuario.getText().toString(), campoPassword.getText().toString(), campoMail.getText().toString());
        Integer existe = usuarioService.validarusuario (usuario, this);
        if (existe>0) {
            Toast.makeText(this, "El usuario ingresado ya existe", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.ETNombre)).setText("");
            ((EditText) findViewById(R.id.ETRepetirPass)).setText("");
            ((EditText) findViewById(R.id.ETEmail)).setText("");
            ((EditText) findViewById(R.id.ETPassword2)).setText("");
            ((EditText) findViewById(R.id.ETUsuario2)).setText("");
        }
        else{
            usuarioService.create(usuario, this);
            Toast.makeText(this, "Usuario creado", Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.ETNombre)).setText("");
            ((EditText) findViewById(R.id.ETRepetirPass)).setText("");
            ((EditText) findViewById(R.id.ETEmail)).setText("");
            ((EditText) findViewById(R.id.ETPassword2)).setText("");
            ((EditText) findViewById(R.id.ETUsuario2)).setText("");
            Intent i1 = new Intent(RegistroActivity.this, MainActivity.class);
            startActivity(i1);
            }

    }

    }
