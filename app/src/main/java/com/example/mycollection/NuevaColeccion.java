package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mycollection.servicies.UsuarioService;

import entidades.Usuario;

public class NuevaColeccion extends AppCompatActivity {

    TextView nuevacoleccion;
    TextView titulo;
    TextView descripcion;
    TextView idtest;// es solo para probrar si pasa el usuario desde Inicio
    Button cancelar;
    Button crear;
    EditText tituloColeccion;
    EditText descripcionColeccion;
    UsuarioService usuarioService = new UsuarioService();
    Usuario u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_coleccion);
        idtest=(TextView)findViewById(R.id.tvId);// es solo para probrar si pasa el usuario desde Inicio
        nuevacoleccion=(TextView)findViewById(R.id.tvNuevaColeccion);
        titulo=(TextView)findViewById((R.id.tvTitulo));
        descripcion=(TextView)findViewById(R.id.tvDescripcion);
        tituloColeccion=(EditText)findViewById((R.id.etTitulo));
        descripcionColeccion=(EditText)findViewById(R.id.etDescripcion);
        cancelar=(Button)findViewById(R.id.btnCancelarCol);
        crear=(Button)findViewById(R.id.btnCrear);
        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), NuevaColeccion.this);
        idtest.setText(u.getId().toString());// es solo para probrar si pasa el usuario desde Inicio

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevaColeccion.this, Inicio.class);
                i.putExtra("Id", u.getId());
                startActivity(i);
                finish();
            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NuevaColeccion.this, AddItem.class);
                i.putExtra("Id", u.getId());
                startActivity(i);
                finish();
            }
        });
    }


}