package com.uniandes.ecos.services;



import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.servicesInterface.IAdministracionService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

@Stateless
public class AdministracionService implements IAdministracionService {
	
	/**
	 * Inyecciï¿½n del contexto de persistencia de la aplicaciï¿½n. 
	 */
	@PersistenceContext
	private EntityManager em;
	
	
	
	

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.servicesInterface.IParametrizacionService#
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
	 * @see com.uniandes.ecos.servicesInterface.IAdministracionService#obtenerRolPorId(long)
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
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.servicesInterface.IAdministracionService#obtenerCiudadano(long)
	 */
	@Override
	public UsuariosCiudadano obtenerCiudadano(long numIdentificacion) throws NegocioException {
		try {			
			BaseDao<UsuariosCiudadano, String> ciudadanoDao = new BaseDao<UsuariosCiudadano, String>(
					UsuariosCiudadano.class, this.em);

			UsuariosCiudadano ciudadano = ciudadanoDao.findById(String.valueOf(numIdentificacion));
			return ciudadano;
		} catch (NoResultException e) {			
			throw new NegocioException(Constantes.ERROR, 0, "No existe ningún ciudadano que correspoda al usuario ingresado.");
		}
	}
	
	

}
