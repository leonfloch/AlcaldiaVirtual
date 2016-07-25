/**
 * 
 */
package com.uniandes.ecos.facadeInterface;

import javax.ejb.Local;

import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.util.SeguridadException;

/**
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
