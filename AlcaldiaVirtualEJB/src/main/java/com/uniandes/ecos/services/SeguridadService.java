/**
 * 
 */
package com.uniandes.ecos.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import com.uniandes.ecos.servicesInterface.ISeguridadService;
import com.uniandes.ecos.util.SeguridadException;



/**
 * @author leonardovalbuenacalderon
 *
 */
@Stateless
public class SeguridadService implements ISeguridadService {

	@PersistenceContext
	private EntityManager em;
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.servicesInterface.ISeguridadService#autenticar(int, java.lang.String)
	 */
	@Override
	public void autenticar(int cedula, String password) throws SeguridadException {
		
		
		
		
		// TODO login temporal		
		if (!(cedula == 1 && password.equals("1"))) {
			throw new SeguridadException("Usuario o clave invalidad");
		} 
	}

	

}
