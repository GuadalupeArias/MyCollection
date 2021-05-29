package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycollection.servicies.UsuarioService;

import entidades.Usuario;

public class Inicio extends AppCompatActivity {

    TextView nombre;
    TextView colecciones;
    Button nueva;
    Button salir;
    int id=0;
    Usuario u;
    UsuarioService usuarioService = new UsuarioService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        nombre=(TextView)findViewById(R.id.tvNombreUsuario);
        colecciones=(TextView)findViewById(R.id.tvMisColecciones);
        nueva=(Button)findViewById(R.id.btnNueva);
        salir=(Button)findViewById(R.id.btnSalir);

        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), Inicio.this);
        nombre.setText("Catálogo de " + u.getNombre());

        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Inicio.this, NuevaColeccion.class);
                i1.putExtra("Id", u.getId());
                startActivity(i1);

            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inicio.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        //es un comenrarui de prueba
    }


}