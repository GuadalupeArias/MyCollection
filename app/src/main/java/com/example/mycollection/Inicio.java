package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycollection.Modelo.AdaptadorColecciones;
import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.servicies.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import entidades.Usuario;
import utilidades.Utilidades;

import static java.lang.Integer.parseInt;

public class Inicio extends AppCompatActivity {

    TextView nombre;
    TextView colecciones;
    ImageButton nueva;
    ImageButton compra;
    ImageButton share;
    Usuario u;
    Colecciones c = new Colecciones();
    UsuarioService usuarioService = new UsuarioService();

    ListView mListView;
    List<Colecciones> mListColeccion;
    ListAdapter mAdapter;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        nombre=(TextView)findViewById(R.id.tvNombreUsuario);
        colecciones=(TextView)findViewById(R.id.tvMisColecciones);
        nueva=findViewById(R.id.btnNueva);
        share=findViewById(R.id.btnShare);
        compra=findViewById(R.id.btnCompra);

        conn=new ConexionSQLiteHelper(this,null,1);
        mListView=findViewById(R.id.listadoColeccLV);
        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), Inicio.this);
        nombre.setText("Catálogo de " + u.getNombre());

        consultarColecciones();

        mAdapter=new AdaptadorColecciones(Inicio.this,R.layout.card_view_colecciones,mListColeccion);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c.setId(mListColeccion.get(position).getId());
                Intent i2 = new Intent(Inicio.this, EditarColeccion.class);
                i2.putExtra("Id",c.getId());
                startActivity(i2);
                finish();
                /*Toast.makeText(Inicio.this, "Id:"+mListColeccion.get(position).getId()+"\n"+
                                "Usuario Id:"+mListColeccion.get(position).getUsuario_id()+"\n"+
                                "Comentario : "+mListColeccion.get(position).getDescripcionColeccion()+"\n"

                        , Toast.LENGTH_LONG).show();*/

            }
        });
        nueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Inicio.this, NuevaColeccion.class);
                i1.putExtra("Id", u.getId());
                startActivity(i1);
                finish();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compartir APP");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, u.getNombre()+" te invita a bajarte la APP My Collection desde https://mycollection.fake/download");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Inicio.this, EventoCompra.class);
                i3.putExtra("Id", u.getId());
                startActivity(i3);
                finish();
            }
        });

    }


    private void consultarColecciones() {

        SQLiteDatabase db=conn.getReadableDatabase();
        Colecciones coleccionObj=null;
        mListColeccion=new ArrayList<Colecciones>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_COLECCION + " WHERE "+Utilidades.CAMPO_USUARIOID_COLECCION+ " =" +u.getId()+ " ORDER BY id DESC;",null);

        while(cursor.moveToNext()){
            coleccionObj=new Colecciones();
            coleccionObj.setId(cursor.getInt(0));
            coleccionObj.setNombreColeccion(cursor.getString(1));
            coleccionObj.setDescripcionColeccion(cursor.getString(2));

            if (cursor.getString(3)!=null){
                coleccionObj.setImagenColeccion(cursor.getString(3));
            }else{
                coleccionObj.setImagenColeccion(null);
            }

            coleccionObj.setUsuario_id(cursor.getString(4));

            mListColeccion.add(coleccionObj);

        }
    }


}