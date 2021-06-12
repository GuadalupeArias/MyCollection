package entidades;

import java.io.Serializable;

public class Items implements Serializable{
     Integer id;
     String nombreItem;
     String anioItem;
     String paisItem;
     String imageItem;
     String descriptionItem;
     String coleccion_id;

    public Items() {
    }
    public Items(Integer id, String nombreItem, String anioItem, String paisItem, String imageItem, String descriptionItem, String coleccion_id) {
        this.id = id;
        this.nombreItem = nombreItem;
        this.anioItem = anioItem;
        this.paisItem = paisItem;
        this.imageItem = imageItem;
        this.descriptionItem = descriptionItem;
        this.coleccion_id = coleccion_id;
    }

    public Items(String nombreItem, String anioItem, String paisItem, String imageItem, String descriptionItem, String coleccion_id) {
        this(null, nombreItem, anioItem, paisItem, imageItem, descriptionItem, coleccion_id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public void setNombreItem(String nombreItem) {
        this.nombreItem = nombreItem;
    }

    public String getAnioItem() {
        return anioItem;
    }

    public void setAnioItem(String anioItem) {
        this.anioItem = anioItem;
    }

    public String getPaisItem() {
        return paisItem;
    }

    public void setPaisItem(String paisItem) {
        this.paisItem = paisItem;
    }

    public String getImageItem() {
        return imageItem;
    }

    public void setImageItem(String imageItem) {
        this.imageItem = imageItem;
    }

    public String getDescriptionItem() {
        return descriptionItem;
    }

    public void setDescriptionItem(String descriptionItem) {
        this.descriptionItem = descriptionItem;
    }

    public String getColeccion_id() {
        return coleccion_id;
    }

    public void setColeccion_id(String coleccion_id) {
        this.coleccion_id = coleccion_id;
    }
}
