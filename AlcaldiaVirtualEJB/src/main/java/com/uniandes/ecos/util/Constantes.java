package com.uniandes.ecos.util;

/**
 * Definici�n de las constantes de la aplicaci�n. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
public class Constantes {
	
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

	//carpeta documentos tramite
	public static final String CARPETA_DOCUMENTOS_TRAMITE = "archivosTramites\\";
	
	//codigos errores
	public static final int CODIGO_ERROR_CARGUE_ARCHIVO = 512;
}
