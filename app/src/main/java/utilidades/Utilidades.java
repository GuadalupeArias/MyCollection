package utilidades;

public class Utilidades {
    //clase con constantes representando los campos y las tablas de la bd
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_USUARIO="usuario";
    public static final String CAMPO_PASSWORD="password";
    public static final String CAMPO_MAIL="mail";


    public static final String CREAR_TABLA_USUARIO="CREATE TABLE "+TABLA_USUARIO+" " +
            "("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_USUARIO+" TEXT, " +
            ""+CAMPO_PASSWORD+" TEXT, "+CAMPO_MAIL+" TEXT)";



    // CREO TABLA COLECCION EN DB

    public static final String TABLA_COLECCION="coleccion";
    public static final String CAMPO_COLECCION_ID="id";
    public static final String CAMPO_NOMBRE_COLECCION="nombreColeccion";
    public static final String CAMPO_DESCRIPCION_COLECCION="descripcionColeccion";
    public static final String CAMPO_IMG_COLECCION="imagenColeccion";
    public static final String CAMPO_USUARIOID_COLECCION="usuario_id";

    public static final String CREAR_TABLA_COLECCION="CREATE TABLE "+TABLA_COLECCION+" " +
            "("+CAMPO_COLECCION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE_COLECCION+" TEXT, "+CAMPO_DESCRIPCION_COLECCION+" TEXT, " +
            ""+CAMPO_IMG_COLECCION+" TEXT, "+CAMPO_USUARIOID_COLECCION+" INTEGER)";

    public static final String TABLA_ITEMS="items";
    public static final String CAMPO_ITEM_ID="id";
    public static final String CAMPO_NOMBRE_ITEM="nombreItem";
    public static final String CAMPO_ANIO_ITEM="anioItem";
    public static final String CAMPO_PAIS_ITEM="paisItem";
    public static final String CAMPO_DESCRIPCION_ITEM="descripcionItem";
    public static final String CAMPO_IMG_ITEM="imagenItem";
    public static final String CAMPO_COLECCIONID_ITEM="coleccion_id";

    public static final String CREAR_TABLA_ITEMS="CREATE TABLE "+TABLA_ITEMS+" " +
            "("+CAMPO_ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_NOMBRE_ITEM+" TEXT, " +CAMPO_ANIO_ITEM+" TEXT, " +CAMPO_PAIS_ITEM+" TEXT, " +CAMPO_DESCRIPCION_ITEM+" TEXT, " +
            ""+CAMPO_IMG_ITEM+" TEXT, "+CAMPO_COLECCIONID_ITEM+" INTEGER)";


}
