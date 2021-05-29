package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.servicies.UsuarioService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import entidades.Usuario;
import utilidades.Utilidades;

import static java.lang.Integer.parseInt;

public class Inicio extends AppCompatActivity {

    TextView nombre;
    TextView colecciones;
    Button nueva;
    Button salir;
    int id=0;
    Usuario u;
    UsuarioService usuarioService = new UsuarioService();


    // mis variables
    ListView mListView;
    List<Colecciones> mListColeccion;
    ListAdapter mAdapter;
    ConexionSQLiteHelper conxDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        nombre=(TextView)findViewById(R.id.tvNombreUsuario);
        colecciones=(TextView)findViewById(R.id.tvMisColecciones);
        nueva=(Button)findViewById(R.id.btnNueva);
        salir=(Button)findViewById(R.id.btnSalir);
        // comienza
        conxDB=new ConexionSQLiteHelper(this,null,1);
        mListView=findViewById(R.id.listadoColeccLV);
        //termina

        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), Inicio.this);
        nombre.setText("Catálogo de " + u.getNombre());


        llenarBasePrueba();
        consultarBase();

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

    private void llenarBasePrueba() {


        SQLiteDatabase db=conxDB.getWritableDatabase();


        for(int i=0;i<3;i++){
            ContentValues values=new ContentValues();
            values.put(Utilidades.CAMPO_NOMBRE_COLECCION,"NOMVRE COLECCION"+i);
            values.put(Utilidades.CAMPO_DESCRIPCION_COLECCION,"SADASDAS ASDAS COLECCION DESCRIPCOPN "+i);
            values.put(Utilidades.CAMPO_USUARIOID_COLECCION,u.getId());
            Long idresultante=db.insert(Utilidades.TABLA_COLECCION,Utilidades.CAMPO_COLECCION_ID,values);
        }

    }

    private void consultarBase() {

        SQLiteDatabase db=conxDB.getReadableDatabase();
        Colecciones coleccionObj=null;
        mListColeccion=new ArrayList<Colecciones>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_COLECCION +" ORDER BY id DESC;",null);

        while(cursor.moveToNext()){
            coleccionObj=new Colecciones();
            coleccionObj.setId(cursor.getInt(0));
            coleccionObj.setNombreColeccion(cursor.getString(1));
            coleccionObj.setDescripcionColeccion(cursor.getString(2));
            // Forma Antigua de publicar la foto
            if (cursor.getString(3)!=null){
                coleccionObj.setImg_Post(cursor.getString(3));
            }else{
                coleccionObj.setImg_Post(null);
            }

            coleccionObj.setUsuario_id(cursor.getString(4));

            mListColeccion.add(coleccionObj);

        }
    }


}