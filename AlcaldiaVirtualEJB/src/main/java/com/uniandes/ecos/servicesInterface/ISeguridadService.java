/**
 * 
 */
package com.uniandes.ecos.servicesInterface;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.UsuarioDto;
import com.uniandes.ecos.util.SeguridadException;

/**
 * @author leonardovalbuenacalderon
 *
 */
@Local
public interface ISeguridadService {
	
	/**
	 * Realiza autenticacion del usuario
	 * @param cedula
	 * @param password
	 */
	void autenticar(int cedula, String password) throws SeguridadException;
	
	/**
	 * Crea un usuario en el sistema
	 * @param usuarioDto
	 * @throws SeguridadException
	 */
	void registrarUsuario(UsuarioDto usuarioDto) throws SeguridadException;

}
