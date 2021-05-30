package com.example.mycollection.Modelo;

public class Usuario {
    private Integer id;
    private String nombre;
    private String usuario;
    private String password;
    private String mail;

    public Usuario(Integer id, String nombre, String usuario, String password, String mail) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.mail = mail;
    }
    public Usuario(String nombre, String usuario, String password, String mail) {
        this(null, nombre, usuario, password, mail);
    }

    public Integer getId() {
        return id;
    }

    public Usuario() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

