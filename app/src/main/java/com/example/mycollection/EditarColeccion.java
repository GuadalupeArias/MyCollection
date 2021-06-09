package com.example.mycollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycollection.Modelo.AdaptadorItems;
import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.servicies.ColeccionesService;

import java.util.ArrayList;
import java.util.List;

import entidades.Items;
import utilidades.Utilidades;

public class EditarColeccion extends AppCompatActivity {

    TextView nombreColec;
    Button agregarItem;
    Button salir;
    TextView items;
    ListView miListView;
    Colecciones c;
    ColeccionesService coleccionesService =new ColeccionesService();
    List<Items> mListItems;
    ListAdapter miAdapter;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_coleccion);
        conn=new ConexionSQLiteHelper(this,null,1);
        nombreColec=(TextView)findViewById(R.id.tvTituloColeccion);
        items=(TextView)findViewById(R.id.tvListaItems);
        agregarItem=(Button)findViewById(R.id.btnAgregarItem);
        salir=(Button)findViewById(R.id.btnSalirItems);
        miListView=findViewById(R.id.listadoItemsLV);

        c=coleccionesService.getColecById(getIntent().getIntExtra("Id",0), EditarColeccion.this);
        nombreColec.setText("Colección: "+c.getNombreColeccion());

        consultarItems();
        miAdapter=new AdaptadorItems(EditarColeccion.this,R.layout.card_view_items,mListItems);
        miListView.setAdapter(miAdapter);

        miListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditarColeccion.this, "Id:"+mListItems.get(position).getId()+"\n"+
                                "Usuario Id:"+mListItems.get(position).getColeccion_id()+"\n"+
                                "Comentario : "+mListItems.get(position).getDescriptionItem()+"\n"

                        , Toast.LENGTH_LONG).show();
            }
        });

        agregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(EditarColeccion.this, AddItem.class);
                i1.putExtra("Id", c.getId());
                startActivity(i1);
                finish();//VER SI ROMPRE TODA
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditarColeccion.this, Inicio.class);
                i.putExtra("Id",Integer.parseInt(c.getUsuario_id()));
                startActivity(i);
                finish();
            }
        });

        /*u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), Inicio.this);
        nombre.setText("Catálogo de " + u.getNombre());
        mAdapter=new AdaptadorColecciones(Inicio.this,R.layout.card_view_colecciones,mListColeccion);
        mListView.setAdapter(mAdapter);
         */
    }

    /*@Override
    void onStart() {
        super.onStart();
    }*/

    private void consultarItems() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Items itemsObj=null;
        mListItems=new ArrayList<Items>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_ITEMS +  " WHERE "+Utilidades.CAMPO_COLECCIONID_ITEM+ " =" +c.getId()+ " ORDER BY id DESC;",null);
        while(cursor.moveToNext()){
            itemsObj=new Items();
            itemsObj.setId(cursor.getInt(0));
            itemsObj.setNombreItem(cursor.getString(1));
            itemsObj.setAnioItem(cursor.getString(2));
            itemsObj.setPaisItem(cursor.getString(3));
            itemsObj.setDescriptionItem(cursor.getString(4));

            if (cursor.getString(5)!=null){
                itemsObj.setImageItem(cursor.getString(5));
            }else{
                itemsObj.setImageItem(null);
            }

            itemsObj.setColeccion_id(cursor.getString(6));

            mListItems.add(itemsObj);

/* private void consultarColecciones() {

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
    }*/

        }
    }
}