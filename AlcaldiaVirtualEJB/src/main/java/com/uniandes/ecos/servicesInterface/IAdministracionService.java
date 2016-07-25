/**
 * 
 */
package com.uniandes.ecos.servicesInterface;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.util.NegocioException;

/**
 * @author 80221940
 *
 */
@Local
public interface IAdministracionService {
	
	/**
	 * Crea un nuevo ciudadano en el sistema
	 * @param ciudadano
	 * @throws NegocioException
	 */
	void registrarCiudadano(UsuariosCiudadano ciudadano) throws NegocioException;
	
	/**
	 * Obtiene el rol por el id
	 * @param id
	 * @return
	 * @throws NegocioException
	 */
	Rol obtenerRolPorId(long id) throws NegocioException;
	
	/**
	 * obtiene un ciudadano por el id
	 * @param numIdentificacion
	 * @return
	 * @throws NegocioException
	 */
	UsuariosCiudadano obtenerCiudadano(long numIdentificacion) throws NegocioException;

}
