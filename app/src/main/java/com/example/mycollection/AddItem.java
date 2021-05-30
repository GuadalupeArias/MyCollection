package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycollection.servicies.UsuarioService;

import com.example.mycollection.Modelo.Usuario;

public class AddItem extends AppCompatActivity {


    UsuarioService usuarioService = new UsuarioService();
    Usuario u;
    Button cancelar;
    Button agregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        cancelar=(Button)findViewById(R.id.btnCancelarItem);
        agregar=(Button)findViewById(R.id.btnAgregar);
        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), AddItem.this);




        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddItem.this, NuevaColeccion.class);
                i.putExtra("Id", u.getId());
                startActivity(i);
                finish();
            }
        });
    }
}