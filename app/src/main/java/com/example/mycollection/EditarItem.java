package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mycollection.servicies.ItemsService;

import entidades.Items;

public class EditarItem extends AppCompatActivity {

    EditText nombre;
    EditText anio;
    EditText pais;
    EditText descripcion;
    ImageView imagenItem;
    Button camara;
    Button galeria;
    Button guardar;
    Button cancelar;
    Button eliminar;
    private  int PICK_IMG_REQUEST=200;
    private Uri imgFilePath;
    private Bitmap imgToStorage;
    String pathImagen;
    private Uri imagenUri;
    Boolean useCam=false;
    Boolean useGallery=false;

    String RUTA_IMAGEN;
    int TOMAR_FOTO=100;
    ConexionSQLiteHelper conn;
    Items item =new Items();
    ItemsService itemsService = new ItemsService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item);

        conn=new ConexionSQLiteHelper(this,null,1);

        item =itemsService.getItemById(getIntent().getIntExtra("Id",0), EditarItem.this);
        nombre=(EditText)findViewById(R.id.etNombreItemEdit);
        anio=(EditText)findViewById(R.id.etAnioItemEdit);
        pais=(EditText)findViewById(R.id.etPaisItemEdit);
        descripcion=(EditText)findViewById(R.id.etDescripItemEdit);
        camara=(Button)findViewById(R.id.btnCamaraEdit);
        galeria=(Button)findViewById(R.id.btnGaleriaEdit);
        guardar=(Button)findViewById(R.id.btnGuardarEdit);
        cancelar = (Button)findViewById(R.id.btnCancelarItemEdit);
        eliminar=(Button)findViewById(R.id.btnEliminarItem);

        nombre.setText(item.getNombreItem());
        pais.setText(item.getPaisItem());
        anio.setText(item.getAnioItem());
        descripcion.setText(item.getDescriptionItem());
        RUTA_IMAGEN=item.getImageItem();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Items aguardar =new Items(item.getId(),nombre.getText().toString(),anio.getText().toString(),pais.getText().toString(),RUTA_IMAGEN,descripcion.getText().toString(),item.getColeccion_id());
                int valorupdate = itemsService.updateItemById(aguardar, EditarItem.this);
                Intent i = new Intent(EditarItem.this, EditarColeccion.class);
                i.putExtra("Id",Integer.parseInt(item.getColeccion_id()));
                startActivity(i);
                finish();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemsService.eliminarItem(item.getId(),EditarItem.this)){
                    Intent i3= new Intent(EditarItem.this, EditarColeccion.class);
                    Toast.makeText(EditarItem.this, "Item eliminado", Toast.LENGTH_LONG).show();
                    i3.putExtra("Id",Integer.parseInt(item.getColeccion_id()));
                    startActivity(i3);
                    finish();
                }
                else {
                    Toast.makeText(EditarItem.this, "ERROR: no se pudo eliminar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}