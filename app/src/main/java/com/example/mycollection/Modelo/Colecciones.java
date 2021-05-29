package com.example.mycollection.Modelo;

import java.io.Serializable;

public class Colecciones implements Serializable {
    Integer id;
    String nombreColeccion;
    String descripcionColeccion;
    String usuario_id;
    String img_Post;

    public Colecciones() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreColeccion() {
        return nombreColeccion;
    }

    public void setNombreColeccion(String nombreColeccion) {
        this.nombreColeccion = nombreColeccion;
    }

    public String getDescripcionColeccion() {
        return descripcionColeccion;
    }

    public void setDescripcionColeccion(String descripcionColeccion) {
        this.descripcionColeccion = descripcionColeccion;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getImg_Post() {
        return img_Post;
    }

    public void setImg_Post(String img_Post) {
        this.img_Post = img_Post;
    }
}
