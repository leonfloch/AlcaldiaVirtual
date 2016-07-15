/**
 * 
 */
package com.uniandes.ecos.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.dtos.UsuarioDto;
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
	public void autenticar(int cedula, String password) throws SeguridadException {
		seguridadService.autenticar(cedula, password);
	}


	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.ISeguridadFacade#
	 * registrarUsuario(com.uniandes.ecos.dtos.UsuarioDto)
	 */
	@Override
	public void registrarUsuario(UsuarioDto usuarioDto)
			throws SeguridadException {
		seguridadService.registrarUsuario(usuarioDto);		
	}

}
