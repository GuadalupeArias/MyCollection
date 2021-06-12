package com.example.mycollection.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mycollection.R;

import java.io.File;
import java.util.List;

import entidades.Items;

public class AdaptadorItems extends ArrayAdapter<Items> {

    List<Items> itemsList;
    public Context context;
    public int resourseLayout;

    public AdaptadorItems(@NonNull Context context, int resource, @NonNull List<Items> objects) {
        super(context, resource, objects);
        this.itemsList = objects;
        this.context = context;
        this.resourseLayout = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_items, null);

        Items items = itemsList.get(position);
        ImageView imagen = view.findViewById(R.id.imgItemIV);
        TextView txtNombreItem = view.findViewById(R.id.nameItemTV);
        TextView txtAnioItem = view.findViewById(R.id.anioItemTV);
        TextView txtDescripcionItem = view.findViewById(R.id.descripItemTV);

        txtNombreItem.setText(items.getNombreItem());
        txtAnioItem.setText(items.getAnioItem());
        txtDescripcionItem.setText(items.getDescriptionItem());

        if (items.getImageItem() != null) {
            File imagenfile = new File(items.getImageItem());
            Bitmap bitmap = BitmapFactory.decodeFile(imagenfile.getAbsolutePath());
            imagen.setImageBitmap(bitmap);
        } else {
            imagen.setImageResource(R.drawable.jeremy_full);
        }

        return view;
    }
}