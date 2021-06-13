package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mycollection.servicies.ItemsService;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import entidades.Items;

public class EditarItem extends AppCompatActivity {

    EditText nombre;
    EditText anio;
    EditText pais;
    EditText descripcion;
    ImageView imagen_item;
    ImageButton camara;
    ImageButton galeria;
    ImageButton guardar;
    ImageButton cancelar;
    ImageButton eliminar;
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
        camara=(ImageButton) findViewById(R.id.btnCamaraEdit);
        galeria=(ImageButton) findViewById(R.id.btnGaleriaEdit);
        guardar=(ImageButton) findViewById(R.id.btnGuardarEdit);
        cancelar = (ImageButton) findViewById(R.id.btnCancelarItemEdit);
        eliminar=(ImageButton) findViewById(R.id.btnEliminarItem);
        imagen_item=(ImageView)findViewById(R.id.imageItemEdit);
        nombre.setText(item.getNombreItem());
        pais.setText(item.getPaisItem());
        anio.setText(item.getAnioItem());
        descripcion.setText(item.getDescriptionItem());
        RUTA_IMAGEN=item.getImageItem();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Items aguardar =new Items(item.getId(),nombre.getText().toString(),anio.getText().toString(),pais.getText().toString(),RUTA_IMAGEN,descripcion.getText().toString(),item.getColeccion_id());
                //int valorupdate =
                itemsService.updateItemById(aguardar, EditarItem.this);
                imgToStorage=null;
                RUTA_IMAGEN=null;
                useGallery=false;
                useCam=false;
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

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(EditarItem.this, EditarColeccion.class);
                i2.putExtra("Id",Integer.parseInt(item.getColeccion_id()));
                startActivity(i2);
                finish();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargarImagenItem();
            }
        });
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirCamara();
            }
        });


    }
    private void cargarImagenItem() {

        useGallery=true;
        useCam=false;
        Intent intento= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intento.setType("image/");
        startActivityForResult(intento,PICK_IMG_REQUEST);
    }

    public void abrirCamara() {
        useCam = true;
        useGallery = false;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;
        try {
            imagenArchivo = crearImagen();
        } catch (IOException ex) {
            Log.e("Error", ex.toString());
        }
        if (imagenArchivo != null) {
            imagenUri = FileProvider.getUriForFile(this, "com.example.mycollection.fileprovider", imagenArchivo);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
            startActivityForResult(intent, TOMAR_FOTO);
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "Foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        RUTA_IMAGEN = imagen.getAbsolutePath();
        return imagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (useGallery){
            if (requestCode== PICK_IMG_REQUEST && resultCode==RESULT_OK && data !=null &&
                    data.getData()!=null) {
                imgFilePath = data.getData();
                RUTA_IMAGEN=getRealPathFromURI(imgFilePath).toLowerCase();
                imagen_item.setImageURI(imgFilePath);
            }
        }
        if (useCam) {
            if (resultCode == RESULT_OK && data != null) {
                File imagenfile=new File(RUTA_IMAGEN);
                imgFilePath = data.getData();

                if (imagenfile.exists()) {
                    imgToStorage= BitmapFactory.decodeFile(imagenfile.getAbsolutePath());

                    imagen_item.setImageBitmap(imgToStorage);
                }

            }
        }

    }
    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation") Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}