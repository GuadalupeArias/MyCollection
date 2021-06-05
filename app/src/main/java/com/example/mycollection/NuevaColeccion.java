package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.servicies.ColeccionesService;
import com.example.mycollection.servicies.UsuarioService;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import entidades.Usuario;
import utilidades.Utilidades;

public class NuevaColeccion extends AppCompatActivity {


    TextView idtest;// es solo para probrar si pasa el usuario desde Inicio
    Button cancelar;
    Button crear;
    EditText tituloColeccion;
    EditText descripcionColeccion;
    UsuarioService usuarioService = new UsuarioService();
    ColeccionesService coleccionesService = new ColeccionesService();
    Usuario u;

    // VARIABLES PARA ALMACENAR FOTOS
    Button galeria;
    Button camara;
    ImageView imagen_coleccion;
    private  int PICK_IMG_REQUEST=200;
    private Uri imgFilePath;
    private Bitmap imgToStorage;
    String pathImagen;
    private Uri imagenUri;
    Boolean useCam=false;
    Boolean useGallery=false;
    //variables tomas
    String RUTA_IMAGEN;
    int TOMAR_FOTO=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_coleccion);
        useCam=false;
        useGallery=false;
        imagen_coleccion=(ImageView)findViewById(R.id.imageView);
        galeria=(Button)findViewById(R.id.galleryBtn);
        camara=(Button)findViewById(R.id.camBtn);


        idtest=(TextView)findViewById(R.id.tvId);// es solo para probrar si pasa el usuario desde Inicio
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

                registrarColeccion();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargar_imagen_coleccion();
            }
        });
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbrirCamara();
            }
        });

    }

    private void cargar_imagen_coleccion() {

        useGallery=true;useCam=false;
        Intent intento= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intento.setType("image/");
        startActivityForResult(intento,PICK_IMG_REQUEST);
    }

    public void AbrirCamara() {
        useCam = true;
        useGallery = false;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;
        try {
            imagenArchivo = CrearImagen();
        } catch (IOException ex) {
            Log.e("Error", ex.toString());
        }
        if (imagenArchivo != null) {
            imagenUri = FileProvider.getUriForFile(this, "com.example.mycollection.fileprovider", imagenArchivo);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
            startActivityForResult(intent, TOMAR_FOTO);

        }
    }
    private File CrearImagen() throws IOException {
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
                imagen_coleccion.setImageURI(imgFilePath);
            }
        }
        if (useCam) {
            if (resultCode == RESULT_OK && data != null) {
                File imagenfile=new File(RUTA_IMAGEN);
                imgFilePath = data.getData();

                if (imagenfile.exists()) {
                    imgToStorage= BitmapFactory.decodeFile(imagenfile.getAbsolutePath());

                    imagen_coleccion.setImageBitmap(imgToStorage);
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


    private void registrarColeccion() {

        Colecciones coleccion = new Colecciones(tituloColeccion.getText().toString(),descripcionColeccion.getText().toString(),u.getId().toString(),RUTA_IMAGEN);
        Long idColeccion= coleccionesService.crearColeccion(coleccion, this);

        Snackbar.make(findViewById(android.R.id.content),"Se ingreso :"+idColeccion,
                Snackbar.LENGTH_LONG).setDuration(5000).show();

        imgToStorage=null;
        RUTA_IMAGEN=null;
        useGallery=false;
        useCam=false;
        Intent intento=new Intent(getApplicationContext(),Inicio.class);
        intento.putExtra("Id", u.getId());
        startActivity(intento);
        onBackPressed();

    }


}