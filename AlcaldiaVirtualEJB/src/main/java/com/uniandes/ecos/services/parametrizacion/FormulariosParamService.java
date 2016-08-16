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
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de crear los formularios dinamicos
 * para los tramites.
 * @author Juan Albarrac�n.
 *
 */
@Stateless
public class FormulariosParamService implements IFormulariosParamService {
	
	/**
	 * Inyecci�n del contexto de persistencia de la aplicaci�n. 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Instanciaci�n del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<Formulario, String> formularioDao;
	
	/**
	 * Inicializaci�n de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.formularioDao = new BaseDao<Formulario, String>(Formulario.class, this.em);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * crearFormulario(com.uniandes.ecos.entities.Formulario)
	 */
	@Override
	public void crearFormulario(Formulario formulario) throws NegocioException {
		this.formularioDao.persist(formulario);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * actualizarFormulario(com.uniandes.ecos.entities.Formulario)
	 */
	@Override
	public void actualizarFormulario(Formulario formulario) throws NegocioException {
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * obtenerTiposCampoForm()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoCampo> obtenerTiposCampoForm() throws NegocioException {
		List<TipoCampo> lstTiposCampo = new ArrayList<>();

		Query query = this.em.createNamedQuery("TipoCampo.findAll");

		//Se ejecuta la consulta
		try {
			lstTiposCampo = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NegocioException(Constantes.ERROR, 0, "No existen tipos de campo parametrizados en el sistema.");
		}
		
		return lstTiposCampo;
	}
	
	
	

}