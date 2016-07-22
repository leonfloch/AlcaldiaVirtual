package com.uniandes.ecos.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.servicesInterface.IAdministracionService;
import com.uniandes.ecos.util.NegocioException;

@Stateless
public class AdministracionService implements IAdministracionService {
	
	/**
	 * Inyección del contexto de persistencia de la aplicación. 
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
		
		//TODO terminar implementacion
				
		ciudadano.setEstado("A");
		ciudadano.setUsuario("q3434");
				
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
		BaseDao<Rol, Long> rolDao = new BaseDao<Rol, Long>(
				Rol.class, this.em);
		
		return rolDao.findById(id);
	}
	
	

}
