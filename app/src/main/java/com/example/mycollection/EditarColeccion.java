package com.example.mycollection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.example.mycollection.Modelo.AdaptadorItems;
import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.servicies.ColeccionesService;

import java.util.ArrayList;
import java.util.List;

import entidades.Items;
import utilidades.Utilidades;

public class EditarColeccion extends AppCompatActivity {

    TextView nombreColec;
    ImageButton agregarItem;
    ImageButton deleteCol;
    //Button salir;
   // Button shareCol;
    TextView items;
    ListView miListView;
    Colecciones c;
    Items item = new Items();
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
        agregarItem=(ImageButton)findViewById(R.id.btnAgregarItem);
        //salir=(Button)findViewById(R.id.btnSalirItems);
        deleteCol=(ImageButton)findViewById(R.id.btnDeleteColec);
        //shareCol=(Button)findViewById(R.id.btnShareColec);
        miListView=findViewById(R.id.listadoItemsLV);

        c=coleccionesService.getColecById(getIntent().getIntExtra("Id",0), EditarColeccion.this);
        nombreColec.setText("Colección: "+c.getNombreColeccion());

        consultarItems();
        miAdapter=new AdaptadorItems(EditarColeccion.this,R.layout.card_view_items,mListItems);
        miListView.setAdapter(miAdapter);

        miListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item.setId(mListItems.get(position).getId());
                Intent i2 = new Intent(EditarColeccion.this, EditarItem.class);
                i2.putExtra("Id",item.getId());
                startActivity(i2);
                finish();
                /*Toast.makeText(EditarColeccion.this, "Id:"+mListItems.get(position).getId()+"\n"+
                                "Usuario Id:"+mListItems.get(position).getColeccion_id()+"\n"+
                                "Comentario : "+mListItems.get(position).getDescriptionItem()+"\n"

                        , Toast.LENGTH_LONG).show();*/
            }
        });

        agregarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(EditarColeccion.this, AddItem.class);
                i1.putExtra("Id", c.getId());
                startActivity(i1);
                finish();
            }
        });

        /*salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditarColeccion.this, Inicio.class);
                i.putExtra("Id",Integer.parseInt(c.getUsuario_id()));
                startActivity(i);
                finish();
            }
        });*/

        deleteCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(EditarColeccion.this);
                alerta.setMessage("¿Esta seguro que desea eliminar la colección?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (coleccionesService.eliminarColeccion(c.getId(),EditarColeccion.this)){
                                    Intent i3= new Intent(EditarColeccion.this, Inicio.class);
                                    Toast.makeText(EditarColeccion.this, "Coleccion eliminada", Toast.LENGTH_LONG).show();
                                    i3.putExtra("Id",Integer.parseInt(c.getUsuario_id()));
                                    startActivity(i3);
                                    finish();
                                }
                                else {
                                    Toast.makeText(EditarColeccion.this, "ERROR: no se pudo eliminar", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar Colección");
                titulo.show();

            }
        });

    }


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


        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(EditarColeccion.this, Inicio.class);
        i.putExtra("Id",Integer.parseInt(c.getUsuario_id()));
        startActivity(i);
        finish();
    }
}