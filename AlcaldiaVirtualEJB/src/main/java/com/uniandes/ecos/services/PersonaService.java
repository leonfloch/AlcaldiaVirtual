package com.uniandes.ecos.services;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.servicesInterface.IPersonaService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementaci�n de los m�todos de parametrizaci�n de personas. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Stateless
public class PersonaService implements IPersonaService {

	/**
	 * Inyecci�n del contexto de persistencia de la aplicaci�n. 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Instanciaci�n del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<Persona, Long> personaDao;
	
	/**
	 * Inicializaci�n de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.personaDao = new BaseDao<Persona, Long>(Persona.class, this.em);
	}
	
	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IPersonaService
	 * #registrarPersona(com.uniandes.ecos.entities.Persona)
	 */
	@Override
	public void registrarPersona(Persona persona) throws NegocioException {
		this.personaDao.persist(persona);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IPersonaService
	 * #obtenerPersona(long)
	 */
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

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IPersonaService
	 * #actualizarPersona(com.uniandes.ecos.entities.Persona)
	 */
	@Override
	public void actualizarPersona(Persona persona) throws NegocioException {
		this.personaDao.merge(persona);
	}

	

}
