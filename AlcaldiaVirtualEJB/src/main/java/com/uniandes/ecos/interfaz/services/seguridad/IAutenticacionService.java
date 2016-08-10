/**
 * 
 */
package com.uniandes.ecos.interfaz.services.seguridad;

import javax.ejb.Local;




import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.util.SeguridadException;

/**
 * Servicio encargado de la parte de autenticacion de los usuarios
 * @author leonardovalbuenacalderon
 *
 */
@Local
public interface IAutenticacionService {
	
	/**
	 * Realiza autenticacion del usuario
	 * @param cedula
	 * @param password
	 * @param esFuncionario
	 * @throws SeguridadException
	 */
	UsuarioSesion autenticar(int cedula, String password, boolean esFuncionario) throws SeguridadException;
	
	

}
