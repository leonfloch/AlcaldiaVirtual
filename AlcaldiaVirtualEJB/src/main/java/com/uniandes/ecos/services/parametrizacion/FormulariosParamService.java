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
import com.uniandes.ecos.entities.CampoFormulario;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de crear los formularios dinamicos
 * para los tramites.
 * @author Juan Albarracín.
 *
 */
@Stateless
public class FormulariosParamService implements IFormulariosParamService {
	
	/**
	 * Inyecciï¿½n del contexto de persistencia de la aplicaciï¿½n. 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Instanciación del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<Formulario, Long> formularioDao;
	
	/**
	 * Inicialización de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.formularioDao = new BaseDao<Formulario, Long>(Formulario.class, this.em);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * crearFormulario(com.uniandes.ecos.entities.Formulario)
	 */
	@Override
	public void crearFormulario(Formulario formulario) throws NegocioException {
		if (existeNombreFormulario(formulario.getNombre())) {
			throw new NegocioException(Constantes.ERROR, 0, "Ya existe un formulario con el mismo nombre, por favor verifique.");
		}else{
			int cont = 0;
			for (CampoFormulario campoAux : formulario.getCamposFormularios()) {
				campoAux.setPosicion(cont);
				cont++; 
			}
			this.formularioDao.persist(formulario);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * actualizarFormulario(com.uniandes.ecos.entities.Formulario)
	 */
	@Override
	public void actualizarFormulario(Formulario formulario) throws NegocioException {
		if (existeNombreFormulario(formulario.getNombre())) {
			throw new NegocioException(Constantes.ERROR, 0, "Ya existe un formulario con el mismo nombre, por favor verifique.");
		}else{
			int cont = 0;
			Formulario formularioAux = this.formularioDao.findById(formulario.getFormularioId());
			formularioAux.setNombre(formulario.getNombre());
			formularioAux.setCamposFormularios(formulario.getCamposFormularios());
			for (CampoFormulario campoAux : formulario.getCamposFormularios()) {
				campoAux.setPosicion(cont);
				cont++; 
			}
			formularioAux.setDocumentosRequeridos(formulario.getDocumentosRequeridos());

			this.formularioDao.merge(formularioAux);
			this.em.flush();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * obtenerFormularios()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Formulario> obtenerFormularios(String nombre) throws NegocioException {
		Query query;
		List<Formulario> lstFormularios = new ArrayList<>();
		
		if (nombre != null && !nombre.isEmpty()) {
			query = this.em.createNamedQuery("Formulario.findByNombreLike");			
			query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
		} else {
			query = this.em.createNamedQuery("Formulario.findAll");
		}	

		//Se ejecuta la consulta
		try {
			lstFormularios = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return lstFormularios;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService#
	 * obtenerFormulario()
	 */
	@Override
	public Formulario obtenerFormulario(long formularioId) throws NegocioException {
		Formulario formulario = this.formularioDao.findById(formularioId);
		return formulario;
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
	
	/**
	 * Verfiica que no exista un formualario con el mismo nombre en la base de datos. 
	 * @return
	 */
	private boolean existeNombreFormulario(String nombre){
		boolean existeNombreFormulario = false;
		
		Query query = this.em.createNamedQuery("Formulario.findByNombre");
		query.setParameter("nombre", nombre.toUpperCase());
		
		try {
			query.getSingleResult();
			existeNombreFormulario = true;
		} catch (NoResultException e) {
		}
		
		return existeNombreFormulario;
	}

}
