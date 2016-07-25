/**
 * 
 */
package com.uniandes.ecos.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.facadeInterface.ISeguridadFacade;
import com.uniandes.ecos.servicesInterface.ISeguridadService;
import com.uniandes.ecos.util.SeguridadException;



/**
 * @author leonardovalbuenacalderon
 *
 */
@Stateless
public class SeguridadFacade implements ISeguridadFacade {
	
	/**
	 * Compomente de seguridad
	 */
	@EJB
	private ISeguridadService seguridadService;

	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.ISeguridadFacade#autenticar(int, java.lang.String)
	 */
	@Override
	public UsuarioSesion autenticar(int cedula, String password, boolean esFuncionario) 
			throws SeguridadException {
		return seguridadService.autenticar(cedula, password, esFuncionario);
	}



}
