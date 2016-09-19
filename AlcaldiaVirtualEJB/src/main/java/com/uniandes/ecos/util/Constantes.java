package com.uniandes.ecos.util;

/**
 * Definici�n de las constantes de la aplicaci�n.
 *
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
public class Constantes {
	
	public static final long MUNICIPIO_ID_DEMO = 002;

    public static final String SESION_USUARIO = "usuario";
    public static final String SESION_MUNICIPIO_CIUDADANO = "sesionMunicipio";

    //manejo de excepciones
    public static final String ADVERTENCIA = "W";
    public static final char ERROR = 'E';
    public static final String INFO = "I";

    //queries de consultas
    public static final String TODOS = "T";
    public static final String ACTIVO = "A";
    public static final String INACTIVO = "I";

    //roles
    public static final long ROL_ADMIN_MINTIC = 1;
    public static final long ROL_ADMIN_LOCAL = 2;
    public static final long ROL_FUNCIONARIO = 3;
    public static final long ROL_CIUDADANO = 4;

    //Definici\u00F3n listas
    public static final String TIPO_ENTRADA_NUMERO = "N\u00famero";
    public static final String TIPO_ENTRADA_NUMERO_VALUE = "Numero";
    public static final String TIPO_ENTRADA_CARACTER = "Caracter";
    public static final String TIPO_ENTRADA_EMAIL = "Email";
    public static final String LBL_ESTADO_ACTIVO = "Activo";
    public static final String LBL_ESTADO_INACTIVO = "Inactivo";
    public static final String TIPO_CAMPO_INPUT = "InputText";

    public static final String USUARIO_SESION = "usuario";

    //carpeta documentos tramite
    public static final String CARPETA_DOCUMENTOS_TRAMITE = "archivosTramites\\";

    //codigos errores
    public static final int CODIGO_ERROR_CARGUE_ARCHIVO = 512;
    public static final int CODIGO_ERROR_ENVIO_CORREO = 513;

    //Varios
    public static final String SI = "S";
    public static final String NO = "N";

    //Administraci\u00F3n de formularios
    public static final String FORMULARIO_MODIFICAR_ID = "formularioId";

}
