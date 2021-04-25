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

}
