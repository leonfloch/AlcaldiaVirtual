package com.uniandes.ecos.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.entities.Funcionalidad;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.PermisoXRol;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementaci�n de los m�todos del m�dulo de parametrizaci�n. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Stateless
public class ParametrizacionFacade implements IParametrizacionFacade{
	
	/**
	 * Inyecci�n de dependencia con servicios de parametrizaci�n de usuarios.
	 */
	@EJB
	private IUsuariosParamService usuariosParamService;
	
	
		

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #registrarFuncionario(com.uniandes.ecos.entities.UsuariosFuncionario)
	 */
	@Override
	public void registrarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		usuariosParamService.registrarFuncionario(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #actualizarFuncionario(com.uniandes.ecos.entities.UsuariosFuncionario)
	 */
	@Override
	public void actualizarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		usuariosParamService.actualizarFuncionario(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #cambiarEstadofuncionario(com.uniandes.ecos.entities.UsuariosFuncionario, java.lang.String)
	 */
	@Override
	public void cambiarEstadofuncionario(UsuariosFuncionario funcionario, String estado) throws NegocioException {
		usuariosParamService.cambiarEstadofuncionario(funcionario, estado);
	}
	
	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #obtenerFuncionarios(long, java.lang.String)
	 */
	@Override
	public List<UsuariosFuncionario> obtenerFuncionarios(long municipioId, String tipoConsulta)
			throws NegocioException {
		return usuariosParamService.obtenerFuncionarios(municipioId, tipoConsulta);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #obtenerFuncionario(java.lang.String)
	 */
	@Override
	public UsuariosFuncionario obtenerFuncionario(String usuario) throws NegocioException {
		return usuariosParamService.obtenerFuncionario(usuario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #registrarPersona(com.uniandes.ecos.entities.Persona)
	 */
	@Override
	public void registrarPersona(Persona persona) throws NegocioException {
		usuariosParamService.registrarPersona(persona);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #obtenerPersona(long)
	 */
	@Override
	public Persona obtenerPersona(long numIdentificacion) throws NegocioException {
		return usuariosParamService.obtenerPersona(numIdentificacion);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #actualizarPersona(com.uniandes.ecos.entities.Persona)
	 */
	@Override
	public void actualizarPersona(Persona persona) throws NegocioException {
		usuariosParamService.actualizarPersona(persona);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.IParametrizacionFacade
	 * #RegistrarCiudadano(com.uniandes.ecos.entities.UsuariosCiudadano)
	 */
	@Override
	public void registrarCiudadano(UsuariosCiudadano ciudadano)
			throws NegocioException {		
		usuariosParamService.registrarCiudadano(ciudadano);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.IParametrizacionFacade#
	 * obtenerCiudadano(long)
	 */
	@Override
	public UsuariosCiudadano obtenerCiudadano(long numIdentificacion) throws NegocioException {
		return usuariosParamService.obtenerCiudadano(numIdentificacion);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerRoles()
	 */
	@Override
	public List<Rol> obtenerRoles() throws NegocioException {
		return usuariosParamService.obtenerRoles();
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#
	 * actualizarRol(com.uniandes.ecos.entities.Rol)
	 */
	@Override
	public void actualizarRol(Rol rol) throws NegocioException {
		usuariosParamService.actualizarRol(rol);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#crearRol(com.uniandes.ecos.entities.Rol)
	 */
	@Override
	public void crearRol(Rol rol) throws NegocioException {
		usuariosParamService.crearRol(rol);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerFuncionalidades()
	 */
	@Override
	public List<Funcionalidad> obtenerFuncionalidades() throws NegocioException {
		return usuariosParamService.obtenerFuncionalidades();
	}

	@Override
	public List<PermisoXRol> obtenerPermisosXRol() throws NegocioException {
		return usuariosParamService.obtenerPermisosXRol();
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerMaxIdRol()
	 */
	@Override
	public long obtenerMaxIdRol() throws NegocioException {
		return usuariosParamService.obtenerMaxIdRol();
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerPersonas()
	 */
	@Override
	public List<Persona> obtenerPersonas() throws NegocioException {
		return usuariosParamService.obtenerPersonas();
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerFuncionarios(java.lang.Long)
	 */
	@Override
	public List<UsuariosFuncionario> obtenerFuncionarios(Long municipioId)
			throws NegocioException {
		return usuariosParamService.obtenerFuncionarios(municipioId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerMunicipio(long)
	 */
	@Override
	public Municipio obtenerMunicipio(long idMunicipio) throws NegocioException {
		return usuariosParamService.obtenerMunicipio(idMunicipio);
	}


	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerMunicipios()
	 */
	@Override
	public List<Municipio> obtenerMunicipios() throws NegocioException {
		return usuariosParamService.obtenerMunicipios();
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParametrizacionFacade#obtenerCiudadanos()
	 */
	@Override
	public List<UsuariosCiudadano> obtenerCiudadanos() throws NegocioException {
		return usuariosParamService.obtenerCiudadanos();
	}

	@Override
	public void actualizarCiudadano(UsuariosCiudadano ciudadano)
			throws NegocioException {
		usuariosParamService.actualizarCiudadano(ciudadano);		
	}
	
	

}
