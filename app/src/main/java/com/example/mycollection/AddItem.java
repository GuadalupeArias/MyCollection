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
import android.widget.TextView;

import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.servicies.ColeccionesService;
import com.example.mycollection.servicies.ItemsService;
import com.example.mycollection.servicies.UsuarioService;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import entidades.Items;
import entidades.Usuario;

public class AddItem extends AppCompatActivity {

    ItemsService itemsService = new ItemsService();
    ColeccionesService coleccionesService = new ColeccionesService();
    Colecciones colecc = new Colecciones();
    //TextView idtest;
    ImageButton cancelar;
    ImageButton agregar;
    EditText nombreItem;
    EditText anioItem;
    EditText paisItem;
    EditText descripItem;
    ImageButton galeria;
    ImageButton camara;
    ImageView imagen_item;
    private  int PICK_IMG_REQUEST=200;
    private Uri imgFilePath;
    private Bitmap imgToStorage;
    private Uri imagenUri;
    Boolean useCam=false;
    Boolean useGallery=false;
    String RUTA_IMAGEN;
    int TOMAR_FOTO=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        useCam=false;
        useGallery=false;
        imagen_item=(ImageView)findViewById(R.id.imageItem);
        galeria=(ImageButton) findViewById(R.id.btnGaleria);
        camara=(ImageButton) findViewById(R.id.btnCamara);
        cancelar=(ImageButton) findViewById(R.id.btnCancelarItem);
        agregar=(ImageButton) findViewById(R.id.btnAgregar);
        nombreItem=(EditText)findViewById(R.id.etNombreItem);
        anioItem=(EditText)findViewById(R.id.etAnioItem);
        paisItem=(EditText)findViewById(R.id.etPaisItem);
        descripItem=(EditText)findViewById(R.id.etDescripItem);
        //idtest=(TextView)findViewById(R.id.tvTestIdColec);
        colecc=coleccionesService.getColecById(getIntent().getIntExtra("Id",0), AddItem.this);
       // idtest.setText(colecc.getId().toString());


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddItem.this, EditarColeccion.class);
                i.putExtra("Id", colecc.getId());
                startActivity(i);
                finish();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registrarItem();
                Intent intento=new Intent(getApplicationContext(),EditarColeccion.class);
                intento.putExtra("Id", colecc.getId());
                startActivity(intento);
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

    private void registrarItem() {

        Items items = new Items(nombreItem.getText().toString(),anioItem.getText().toString(),paisItem.getText().toString(),RUTA_IMAGEN,descripItem.getText().toString(),colecc.getId().toString());
        Long idItem= itemsService.crearItem(items, this);

        Snackbar.make(findViewById(android.R.id.content),"Se ingreso :"+idItem,
                Snackbar.LENGTH_LONG).setDuration(5000).show();

        imgToStorage=null;
        RUTA_IMAGEN=null;
        useGallery=false;
        useCam=false;

    }
}