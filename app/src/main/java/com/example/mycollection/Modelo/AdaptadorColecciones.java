package com.example.mycollection.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.mycollection.R;

import java.io.File;
import java.util.List;

public class AdaptadorColecciones extends ArrayAdapter<Colecciones> {
    List<Colecciones> coleccionesList;
    public Context context;
    public int resourseLayout;


    public AdaptadorColecciones(@NonNull Context context, int resource, @NonNull List<Colecciones> objects) {
        super(context, resource, objects);
        this.coleccionesList=objects;
        this.context=context;
        this.resourseLayout=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_view_colecciones,null);

        Colecciones coleccion=coleccionesList.get(position);
        ImageView imagen=view.findViewById(R.id.imgCollectionIV);
        TextView txtNombreCole=view.findViewById(R.id.nameColletionTV);
        TextView txtDescripcionCole=view.findViewById(R.id.descripColletionTV);

        txtNombreCole.setText(coleccion.getNombreColeccion());
        txtDescripcionCole.setText(coleccion.getDescripcionColeccion());

        if (coleccion.getImagenColeccion() !=null){
            File imagenfile=new File(coleccion.getImagenColeccion());
            Bitmap bitmap = BitmapFactory.decodeFile(imagenfile.getAbsolutePath());
            imagen.setImageBitmap(bitmap);
        }else{
            imagen.setImageResource(R.drawable.jeremy_full);
        }

        return view;
    }
}
