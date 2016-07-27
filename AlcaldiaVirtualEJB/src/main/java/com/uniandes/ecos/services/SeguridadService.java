/**
 * 
 */
package com.uniandes.ecos.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;









import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.servicesInterface.IAdministracionService;
import com.uniandes.ecos.servicesInterface.IFuncionarioService;
import com.uniandes.ecos.servicesInterface.ISeguridadService;
import com.uniandes.ecos.util.NegocioException;
import com.uniandes.ecos.util.SeguridadException;



/**
 * @author leonardovalbuenacalderon
 *
 */
@Stateless
public class SeguridadService implements ISeguridadService {

	@PersistenceContext
	private EntityManager em;
	
	
	@EJB
	IAdministracionService adminService;
	
	@EJB
	IFuncionarioService funcionarioService;
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.servicesInterface.ISeguridadService#autenticar(int, java.lang.String)
	 */
	@Override
	public UsuarioSesion autenticar(int cedula, String password, boolean esFuncionario) 
			throws SeguridadException {
		
		if (esFuncionario) {
			return this.loginPorFuncionario(cedula, password);			
		} else {			
			return this.loginPorCiudadano(cedula, password);
		}
	}
	
	/**
	 * login por ciudadano
	 * @param cedula
	 * @param password
	 * @throws SeguridadException
	 */
	private UsuariosCiudadano loginPorCiudadano(int cedula, String password) throws SeguridadException {
		try {
			UsuariosCiudadano ciudadano = adminService.obtenerCiudadano(cedula);			
			if (ciudadano == null || !ciudadano.getContrasenia().equals(password)) {
				throw new SeguridadException("Usuario o clave invalida");
			}
			return ciudadano;
		} catch (NegocioException e) {
			throw new SeguridadException("Usuario o clave invalida");
		}
	}
	
	/**
	 * login por funcionario
	 * @param cedula
	 * @param password
	 * @throws SeguridadException
	 */
	private UsuariosFuncionario loginPorFuncionario(int cedula, String password) throws SeguridadException {
		try {
			UsuariosFuncionario funcionario = funcionarioService.obtenerFuncionario(String.valueOf(cedula));
			if (funcionario == null || !funcionario.getContrasenia().equals(password)) {
				throw new SeguridadException("Usuario o clave invalida");
			}
			return funcionario;
		} catch (NegocioException e) {
			throw new SeguridadException("Usuario o clave invalida");
		}
	}

	

}
