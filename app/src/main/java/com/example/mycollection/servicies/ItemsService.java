package com.example.mycollection.servicies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycollection.ConexionSQLiteHelper;
import com.example.mycollection.Modelo.Colecciones;

import entidades.Items;
import utilidades.Utilidades;

public class ItemsService {
    public long crearItem(Items items, Context context) {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,null, 1);

        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_ITEM, items.getNombreItem());
        values.put(Utilidades.CAMPO_ANIO_ITEM,items.getAnioItem());
        values.put(Utilidades.CAMPO_PAIS_ITEM,items.getPaisItem());
        values.put(Utilidades.CAMPO_DESCRIPCION_ITEM,items.getDescriptionItem());
        values.put(Utilidades.CAMPO_IMG_ITEM,items.getImageItem());
        values.put(Utilidades.CAMPO_COLECCIONID_ITEM,Integer.parseInt(items.getColeccion_id()));

        Long idresultante=db.insert(Utilidades.TABLA_ITEMS,Utilidades.CAMPO_ITEM_ID,values);
        db.close();
        return idresultante;

    }


    public boolean eliminarItem (int id, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM " +Utilidades.TABLA_ITEMS+" WHERE "
                +Utilidades.CAMPO_ITEM_ID+" ="+id,null);
        cursor.moveToNext();
        cursor.close();
        db.close();
        conn.close();
        return true;
    }

    public Items getItemById (int id, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_ITEMS+" WHERE "
                +Utilidades.CAMPO_ITEM_ID+" ="+id,null);
        if (cursor.moveToFirst()){
            Items ret = convertToItem(cursor);
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
    public int updateItemById (Items item, Context context){

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,null, 1);
        SQLiteDatabase db=conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE_ITEM, item.getNombreItem());
        values.put(Utilidades.CAMPO_ANIO_ITEM,item.getAnioItem());
        values.put(Utilidades.CAMPO_PAIS_ITEM,item.getPaisItem());
        values.put(Utilidades.CAMPO_DESCRIPCION_ITEM,item.getDescriptionItem());
        values.put(Utilidades.CAMPO_IMG_ITEM,item.getImageItem());
        //values.put(Utilidades.CAMPO_COLECCIONID_ITEM,Integer.parseInt(item.getColeccion_id()));
        /*Cursor cursor = db.rawQuery("UPDATE " +Utilidades.TABLA_ITEMS+" WHERE "
                +Utilidades.CAMPO_ITEM_ID+" = "+item.getId(),null);
        cursor.moveToNext();/*
                 */

        return db.update(Utilidades.TABLA_ITEMS,values,Utilidades.CAMPO_ITEM_ID +" = ?", new String[] { String.valueOf(item.getId()) });

    }
    private Items convertToItem(Cursor cursor) {
        Items items = new Items();
        items.setId(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_ITEM_ID)));
        items.setNombreItem(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_NOMBRE_ITEM)));
        items.setDescriptionItem(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_DESCRIPCION_ITEM)));
        items.setPaisItem(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_PAIS_ITEM)));
        items.setAnioItem(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_ANIO_ITEM)));
        items.setImageItem(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_IMG_ITEM)));
        items.setColeccion_id(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_COLECCIONID_ITEM)));
        return items;
    }


}
