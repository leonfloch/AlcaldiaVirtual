/**
 * 
 */
package com.uniandes.ecos.services.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.entities.Funcionalidad;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.PermisoXRol;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de realizar la parametrizacion de los usuarios
 * en el sistema.
 * @author 80221940
 *
 */
@Stateless
public class UsuariosParamService implements IUsuariosParamService {
	
	/**
	 * Inyecci�n del contexto de persistencia de la aplicaci�n. 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Instanciaci�n del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<UsuariosFuncionario, String> funcionarioDao;
	
	/**
	 * Instanciaci�n del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<Persona, Long> personaDao;
	
	/**
	 * Inicializaci�n de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.funcionarioDao = new BaseDao<UsuariosFuncionario, String>(UsuariosFuncionario.class, this.em);
		this.personaDao = new BaseDao<Persona, Long>(Persona.class, this.em);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#
	 * registrarCiudadano(com.uniandes.ecos.entities.UsuariosCiudadano)
	 */
	@Override
	public void registrarCiudadano(UsuariosCiudadano ciudadano)
			throws NegocioException {
		//validacion claves
		if (!ciudadano.getContrasenia().equals(ciudadano.getContraseniaVal())) {
			throw new NegocioException(Constantes.ERROR, 0, "Las claves no coinciden.");
		}		
		//valida si el usuario ya existe
		if (obtenerCiudadano(ciudadano.getPersona().getNumIdentificacion()) != null) {
			throw new NegocioException(Constantes.ERROR, 0, "El Ciudadano ya existe.");
		}

		ciudadano.getPersona().setTipoIdentificacion("CC");		
		ciudadano.setEstado(Constantes.ACTIVO);
		ciudadano.setUsuario(String.valueOf(ciudadano.getPersona().getNumIdentificacion()));				
		ciudadano.setRole(this.obtenerRolPorId(4));

		BaseDao<UsuariosCiudadano, String> ciudadanoDao = new BaseDao<UsuariosCiudadano, String>(
				UsuariosCiudadano.class, this.em);

		ciudadanoDao.persist(ciudadano);

	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#
	 * obtenerCiudadano(long)
	 */
	@Override
	public UsuariosCiudadano obtenerCiudadano(long numIdentificacion) throws NegocioException {
		try {			
			BaseDao<UsuariosCiudadano, String> ciudadanoDao = new BaseDao<UsuariosCiudadano, String>(
					UsuariosCiudadano.class, this.em);

			UsuariosCiudadano ciudadano = ciudadanoDao.findById(String.valueOf(numIdentificacion));
			return ciudadano;
		} catch (NoResultException e) {			
			throw new NegocioException(Constantes.ERROR, 0, "No existe ning�n ciudadano que correspoda al usuario ingresado.");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#
	 * obtenerRolPorId(long)
	 */
	@Override
	public Rol obtenerRolPorId(long id) throws NegocioException {
		try {
			BaseDao<Rol, Long> rolDao = new BaseDao<Rol, Long>(
					Rol.class, this.em);
			return rolDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public void registrarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		this.funcionarioDao.persist(funcionario);
	}

	
	@Override
	public void actualizarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		this.funcionarioDao.merge(funcionario);
	}

	
	@Override
	public void cambiarEstadofuncionario(UsuariosFuncionario funcionario, String estado) throws NegocioException {
		funcionario = this.funcionarioDao.findById(funcionario.getUsuario());
		funcionario.setEstado(estado);
		this.funcionarioDao.merge(funcionario);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuariosFuncionario> obtenerFuncionarios(long municipioId, String tipoConsulta) throws NegocioException {
		Query query;
		List<UsuariosFuncionario> lstFuncionarios = new ArrayList<>();
		
		//Se valida el tipo de consulta a realizar.
		if (Constantes.TODOS.equals(tipoConsulta)) {
			query = this.em.createNamedQuery("UsuariosFuncionario.findByAlcaldia");			
			query.setParameter("municipioId", municipioId);
		} else{
			query = this.em.createNamedQuery("UsuariosFuncionario.findByEstado");
			query.setParameter("municipioId", municipioId);
			query.setParameter("estado", tipoConsulta);
		}
		
		//Se ejecuta la consulta
		try {
			lstFuncionarios = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NegocioException(Constantes.ERROR, 0, "No existen funcionarios parametrizados en esta alcald�a.");
		}
		
		return lstFuncionarios;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerFuncionarios(java.lang.Long)
	 */
	@Override
	public List<UsuariosFuncionario> obtenerFuncionarios(Long municipioId)
			throws NegocioException {		
		Query query;
		List<UsuariosFuncionario> lstFuncionarios = new ArrayList<>();
		
		if (municipioId > 0) {
			query = this.em.createNamedQuery("UsuariosFuncionario.findByAlcaldia");			
			query.setParameter("municipioId", municipioId);
		} else {
			query = this.em.createNamedQuery("UsuariosFuncionario.findAll");
		}		
		//Se ejecuta la consulta
		try {
			lstFuncionarios = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NegocioException(Constantes.ERROR, 0, "No existen funcionarios parametrizados en esta alcald�a.");
		}
		
		return lstFuncionarios;
	}

	
	@Override
	public UsuariosFuncionario obtenerFuncionario(String usuario) throws NegocioException {
		try {
			UsuariosFuncionario funcionario = this.funcionarioDao.findById(usuario);
			return funcionario;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NegocioException(Constantes.ERROR, 0, "No existe ning�n funcionario que correspoda al usuario ingresado.");
		}
	}
	
	@Override
	public void registrarPersona(Persona persona) throws NegocioException {
		this.personaDao.persist(persona);
	}

	
	@Override
	public Persona obtenerPersona(long numIdentificacion) throws NegocioException {
		try {
			Persona persona = this.personaDao.findById(numIdentificacion);
			return persona;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public void actualizarPersona(Persona persona) throws NegocioException {
		this.personaDao.merge(persona);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerRoles()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> obtenerRoles() throws NegocioException {
		
		List<Rol> roles = new ArrayList<Rol>();
		try {
			Query query = this.em.createNamedQuery("Rol.findAll");		
			roles = query.getResultList();
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen roles parametrizados en el sistema.");
		}
		return roles;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.
	 * IUsuariosParamService#actualizarRol(com.uniandes.ecos.entities.Rol)
	 */
	@Override
	public void actualizarRol(Rol rol) throws NegocioException {
		BaseDao<Rol, Long> rolDao = new BaseDao<Rol, Long>(Rol.class, this.em);
		rolDao.merge(rol);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#crearRol(com.uniandes.ecos.entities.Rol)
	 */
	@Override
	public void crearRol(Rol rol) throws NegocioException {
		BaseDao<Rol, Long> rolDao = new BaseDao<Rol, Long>(Rol.class, this.em);
		rolDao.persist(rol);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerFuncionalidades()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionalidad> obtenerFuncionalidades() throws NegocioException {
		List<Funcionalidad> funcionalidades = new ArrayList<Funcionalidad>();
		try {
			Query query = this.em.createNamedQuery("Funcionalidad.findAll");		
			funcionalidades = query.getResultList();
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen funcionalidades parametrizados en el sistema.");
		}
		return funcionalidades;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerPermisosXRol()
	 */
	@Override
	public List<PermisoXRol> obtenerPermisosXRol() throws NegocioException {
		List<PermisoXRol> permisosXrolList = new ArrayList<PermisoXRol>();
		try {
			Query query = this.em.createNamedQuery("PermisoXRol.findAll");		
			permisosXrolList = query.getResultList();
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen permisos x rol parametrizados en el sistema.");
		}
		return permisosXrolList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerMaxIdRol()
	 */
	@Override
	public long obtenerMaxIdRol() throws NegocioException {
		long max = (long) em.createQuery("select max(r.rolId) from Rol r").getSingleResult();		
		return max;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerPersonas()
	 */
	@Override
	public List<Persona> obtenerPersonas() throws NegocioException {
		List<Persona> personasList = new ArrayList<Persona>();
		try {
			Query query = this.em.createNamedQuery("Persona.findAll");		
			personasList = query.getResultList();
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen personas parametrizados en el sistema.");
		}
		return personasList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.
	 * IUsuariosParamService#obtenerMunicipio(long)
	 */
	@Override
	public Municipio obtenerMunicipio(long idMunicipio) throws NegocioException {
		BaseDao<Municipio, Long> municipioDao = new BaseDao<Municipio, Long>(Municipio.class, this.em);
		Municipio municipio = null;
		try {			
			municipio = municipioDao.findById(idMunicipio);
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen el municipio buscado.");
		}
		
		
		
		return municipio;		
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerMunicipios()
	 */
	@Override
	public List<Municipio> obtenerMunicipios() throws NegocioException {
		List<Municipio> municipiosList = new ArrayList<Municipio>();
		try {
			Query query = this.em.createNamedQuery("Municipio.findAll");		
			municipiosList = query.getResultList();
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen municipios parametrizados en el sistema.");
		}
		return municipiosList;		
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#obtenerCiudadanos()
	 */
	@Override
	public List<UsuariosCiudadano> obtenerCiudadanos() throws NegocioException {
		List<UsuariosCiudadano> ciudadanos = new ArrayList<UsuariosCiudadano>();
		try {
			Query query = this.em.createNamedQuery("UsuariosCiudadano.findAll");		
			ciudadanos = query.getResultList();
		} catch (NoResultException e) {
			throw new NegocioException(Constantes.ERROR, 0, "No existen ciudadanos parametrizados en el sistema.");
		}
		return ciudadanos;
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IUsuariosParamService#actualizarCiudadano(com.uniandes.ecos.entities.UsuariosCiudadano)
	 */
	@Override
	public void actualizarCiudadano(UsuariosCiudadano ciudadano)
			throws NegocioException {
		BaseDao<UsuariosCiudadano, String> ciudadanoDao = new BaseDao<UsuariosCiudadano, String>(
				UsuariosCiudadano.class, this.em);
		ciudadanoDao.merge(ciudadano);
	}

	



}
