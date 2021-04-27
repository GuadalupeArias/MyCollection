package com.example.mycollection.servicies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mycollection.ConexionSQLiteHelper;
import com.example.mycollection.MainActivity;

import java.util.ArrayList;
import java.util.Collection;

import entidades.Usuario;
import utilidades.Utilidades;

public class UsuarioService {

    public void create (Usuario usuario, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,null, 1);

        SQLiteDatabase db=conn.getWritableDatabase();
        String insert="INSERT INTO "+ Utilidades.TABLA_USUARIO+" ( "+Utilidades.CAMPO_NOMBRE+"," +Utilidades.CAMPO_USUARIO+","+Utilidades.CAMPO_PASSWORD+","+Utilidades.CAMPO_MAIL+") " +
                "VALUES ('"+usuario.getNombre()+"' , '"+usuario.getUsuario()+"' , '"+usuario.getPassword()+"' , '"+usuario.getMail()+"')";

        db.execSQL(insert);
        db.close();
    }

    public Collection<Usuario> buscarUsuarios (Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO,null);
        Collection <Usuario> usuarios = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                usuarios.add(convertToUser (cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        conn.close();
        return usuarios;
    }



    public Usuario getUserByUserAndPass (String u, String p, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO+" WHERE "
                +Utilidades.CAMPO_USUARIO+" ='"+u+"' AND " +Utilidades.CAMPO_PASSWORD+ " ='" +p+"'",null);
        if (cursor.moveToFirst()){
            return convertToUser(cursor);
        }
        return null;
    }

    public Usuario getUserById (int id, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context, null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO+" WHERE "
                +Utilidades.CAMPO_ID+" ="+id,null);
        if (cursor.moveToFirst()){
            return convertToUser(cursor);
        }
        return null;
    }



        public Integer validarusuario(Usuario usuario, Context context){
            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,null, 1);
            SQLiteDatabase db=conn.getReadableDatabase();
            Integer existe;
            Cursor cursor = db.rawQuery("SELECT COUNT (" + Utilidades.CAMPO_USUARIO + ") FROM "
                    +Utilidades.TABLA_USUARIO + " WHERE USUARIO = '" + usuario.getUsuario().toString() + "'",null);
            cursor.moveToNext();
            existe= cursor.getInt(0);
            cursor.close();
            db.close();
            conn.close();
            return existe;
        }

    private Usuario convertToUser(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_ID)));
        usuario.setNombre(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_NOMBRE)));
        usuario.setUsuario(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_USUARIO)));
        usuario.setPassword(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_PASSWORD)));
        usuario.setMail(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_MAIL)));
        return usuario;
    }


}

