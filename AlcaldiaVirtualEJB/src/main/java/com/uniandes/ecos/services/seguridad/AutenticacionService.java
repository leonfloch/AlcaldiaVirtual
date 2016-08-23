/**
 * 
 */
package com.uniandes.ecos.services.seguridad;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService;
import com.uniandes.ecos.interfaz.services.seguridad.IAutenticacionService;
import com.uniandes.ecos.util.NegocioException;
import com.uniandes.ecos.util.SeguridadException;



/**
 * @author leonardovalbuenacalderon
 *
 */
@Stateless
public class AutenticacionService implements IAutenticacionService {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Servicio que maneja la parametrizacion de usuarios
	 */
	@EJB
	private IUsuariosParamService usuariosParamService;
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.servicesInterface.ISeguridadService#autenticar(int, java.lang.String)
	 */
	@Override
	public UsuarioSesion autenticar(int cedula, String password, boolean esFuncionario) 
			throws SeguridadException {
		
		UsuarioSesion usuario = null;
		if (esFuncionario) {
			usuario = this.loginPorFuncionario(cedula, password);			
		} else {			
			usuario = this.loginPorCiudadano(cedula, password);
		}		
		if (usuario.getMunicipioId() > 0) {
			//se carga el objeto municipio al usuario
			try {
				Municipio municipio = usuariosParamService.obtenerMunicipio(usuario.getMunicipioId());
				usuario.setMunicipio(municipio);
			} catch (NegocioException e) {		
				throw new SeguridadException(e.getMensaje());
			}
		}		
		return usuario;
	}
	
	/**
	 * login por ciudadano
	 * @param cedula
	 * @param password
	 * @throws SeguridadException
	 */
	private UsuariosCiudadano loginPorCiudadano(int cedula, String password) throws SeguridadException {
		try {
			UsuariosCiudadano ciudadano = usuariosParamService.obtenerCiudadano(cedula);			
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
			UsuariosFuncionario funcionario = usuariosParamService.obtenerFuncionario(String.valueOf(cedula));
			if (funcionario == null || !funcionario.getContrasenia().equals(password)) {
				throw new SeguridadException("Usuario o clave invalida");
			}
			return funcionario;
		} catch (NegocioException e) {
			throw new SeguridadException("Usuario o clave invalida");
		}
	}

	

}
