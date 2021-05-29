package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mycollection.servicies.UsuarioService;

import entidades.Usuario;

public class Inicio extends AppCompatActivity {

    TextView nombre;
    int id=0;
    Usuario u;
    UsuarioService usuarioService = new UsuarioService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre=(TextView)findViewById(R.id.tvNombreUsuario);
        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), Inicio.this);
        nombre.setText(u.getNombre());
        //es un comenrarui de prueba
    }
}