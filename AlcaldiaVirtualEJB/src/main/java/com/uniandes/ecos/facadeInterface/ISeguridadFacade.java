/**
 * 
 */
package com.uniandes.ecos.facadeInterface;

import javax.ejb.Local;

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
	 */
	void autenticar(int cedula, String password) throws SeguridadException;
	
	

}
