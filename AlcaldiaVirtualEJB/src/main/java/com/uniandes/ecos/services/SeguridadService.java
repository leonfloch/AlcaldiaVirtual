/**
 * 
 */
package com.uniandes.ecos.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;








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
	public void autenticar(int cedula, String password, boolean esFuncionario) 
			throws SeguridadException {
		
		if (esFuncionario) {
			this.loginPorFuncionario(cedula, password);			
		} else {			
			this.loginPorCiudadano(cedula, password);
		}
	}
	
	/**
	 * login por ciudadano
	 * @param cedula
	 * @param password
	 * @throws SeguridadException
	 */
	private void loginPorCiudadano(int cedula, String password) throws SeguridadException {
		try {
			UsuariosCiudadano ciudadano = adminService.obtenerCiudadano(cedula);			
			if (ciudadano == null || !ciudadano.getContrasenia().equals(password)) {
				throw new SeguridadException("Usuario o clave invalidad");
			}
		} catch (NegocioException e) {
			throw new SeguridadException("Usuario o clave invalidad");
		}
	}
	
	/**
	 * login por funcionario
	 * @param cedula
	 * @param password
	 * @throws SeguridadException
	 */
	private void loginPorFuncionario(int cedula, String password) throws SeguridadException {
		try {
			UsuariosFuncionario funcionario = funcionarioService.obtenerFuncionario(String.valueOf(cedula));
			if (funcionario == null || !funcionario.getContrasenia().equals(password)) {
				throw new SeguridadException("Usuario o clave invalidad");
			}
		} catch (NegocioException e) {
			throw new SeguridadException("Usuario o clave invalidad");
		}
	}

	

}
