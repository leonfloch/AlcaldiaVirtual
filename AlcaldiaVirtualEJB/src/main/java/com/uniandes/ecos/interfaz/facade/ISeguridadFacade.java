/**
 * 
 */
package com.uniandes.ecos.interfaz.facade;

import javax.ejb.Local;

import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.util.SeguridadException;

/**
 * Fachada encargada de toda la parte de seguridad de la aplicacion
 * @author leonardovalbuenacalderon
 *
 */
@Local
public interface ISeguridadFacade {
	
	/**
	 * Realiza autenticacion del usuario
	 * @param cedula
	 * @param password
	 * @param esFuncionario
	 * @throws SeguridadException
	 */
	UsuarioSesion autenticar(int cedula, String password, boolean esFuncionario) throws SeguridadException;
	
	

}
