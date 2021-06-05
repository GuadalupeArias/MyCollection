package com.example.mycollection.Modelo;

import java.io.Serializable;

public class Colecciones implements Serializable {
    Integer id;
    String nombreColeccion;
    String descripcionColeccion;
    String usuario_id;
    String imagenColeccion;

    public Colecciones() {
    }

    public Colecciones( String nombreColeccion, String descripcionColeccion, String usuario_id, String imgagenColeccion) {
        this.nombreColeccion = nombreColeccion;
        this.descripcionColeccion = descripcionColeccion;
        this.usuario_id = usuario_id;
        this.imagenColeccion = imgagenColeccion;
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

    public String getImagenColeccion() {
        return imagenColeccion;
    }

    public void setImagenColeccion(String imgagenColeccion) {
        this.imagenColeccion = imgagenColeccion;
    }
}
