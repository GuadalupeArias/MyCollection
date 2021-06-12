package com.example.mycollection.servicies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycollection.ConexionSQLiteHelper;
import com.example.mycollection.Inicio;
import com.example.mycollection.Modelo.Colecciones;
import com.example.mycollection.NuevaColeccion;
import com.google.android.material.snackbar.Snackbar;

import entidades.Usuario;
import utilidades.Utilidades;

public class ColeccionesService {

    public long crearColeccion(Colecciones coleccion, Context context) {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,null, 1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_COLECCION, coleccion.getNombreColeccion());
        values.put(Utilidades.CAMPO_DESCRIPCION_COLECCION,coleccion.getDescripcionColeccion());
        values.put(Utilidades.CAMPO_USUARIOID_COLECCION,Integer.parseInt(coleccion.getUsuario_id()));
        values.put(Utilidades.CAMPO_IMG_COLECCION,coleccion.getImagenColeccion());

        Long idresultante=db.insert(Utilidades.TABLA_COLECCION,Utilidades.CAMPO_ID,values);
        return idresultante;

    }

    public Colecciones getColecById (int id, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_COLECCION+" WHERE "
                +Utilidades.CAMPO_COLECCION_ID+" ="+id,null);
        if (cursor.moveToFirst()){
            Colecciones ret = convertToColec(cursor);
            cursor.close();
            db.close();
            conn.close();
            return ret;

        }
        cursor.close();
        db.close();
        conn.close();
        return null;
    }

    private Colecciones convertToColec(Cursor cursor) {
        Colecciones coleccion = new Colecciones();
        coleccion.setId(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_COLECCION_ID)));
        coleccion.setNombreColeccion(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_NOMBRE_COLECCION)));
        coleccion.setDescripcionColeccion(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_DESCRIPCION_COLECCION)));
        coleccion.setImagenColeccion(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_IMG_COLECCION)));
        coleccion.setUsuario_id(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_USUARIOID_COLECCION)));
        return coleccion;
    }

    public boolean eliminarColeccion (int id, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM " +Utilidades.TABLA_COLECCION+" WHERE "
                +Utilidades.CAMPO_COLECCION_ID+" ="+id,null);
        cursor.moveToNext();
        cursor.close();
        db.close();
        conn.close();
        return true;
    }
}
