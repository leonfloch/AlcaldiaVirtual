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
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.interfaz.services.parametrizacion.ITramitesParamService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementacion Servicio encargado de crear los tipo tramites
 *
 * @author 80221940
 *
 */
@Stateless
public class TramitesParamService implements ITramitesParamService {

	/**
	 * Inyecciï¿½n del contexto de persistencia de la aplicaciï¿½n.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Instanciación del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<TipoTramite, Long> tipoTramiteDao;

	/**
	 * Instanciación del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<TramiteXMunicipio, Long> tramiteXMunicipio;

	/**
	 * Inicialización de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.tipoTramiteDao = new BaseDao<TipoTramite, Long>(TipoTramite.class, this.em);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.
	 * ITramitesParamService# crearTipoTramite()
	 */
	@Override
	public void crearTipoTramite(TipoTramite tipoTramite) throws NegocioException {
		this.tipoTramiteDao.persist(tipoTramite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.
	 * ITramitesParamService# actualizarTipoTramite()
	 */
	@Override
	public void actualizarTipoTramite(TipoTramite tipoTramite) throws NegocioException {
		this.tipoTramiteDao.merge(tipoTramite);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.
	 * ITramitesParamService# obtenerListaTramites(String nombre)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoTramite> obtenerListaTiposTramites(String nombre) throws NegocioException {
		Query query;
		List<TipoTramite> lstTipoTramite = new ArrayList<>();

		if (nombre != null && !nombre.isEmpty()) {
			query = this.em.createNamedQuery("TipoTramite.findByNombreLike");
			query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
		} else {
			query = this.em.createNamedQuery("TipoTramite.findAll");
		}

		// Se ejecuta la consulta
		try {
			lstTipoTramite = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}

		return lstTipoTramite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerListaMunicipios()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> obtenerListaMunicipios() throws NegocioException {
		return this.em.createNamedQuery("Municipio.findAll").getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerListaMunicipiosXTipoTramite()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Municipio> obtenerListaMunicipiosXTipoTramite(long tipoTramiteId) throws NegocioException {
		String sql = "Select tm.municipio From TramiteXMunicipio tm Where tm.tiposTramite.tipoTramiteId = :tipoTramiteId and tm.estado = :estado";
		Query query = this.em.createQuery(sql);
		query.setParameter("tipoTramiteId", tipoTramiteId);
		query.setParameter("estado", "A");
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * crearTramiteXMunicipio()
	 */
	@Override
	public void crearTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException {
		this.tramiteXMunicipio.persist(tramiteXMunicipio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * actualizarTramiteXMunicipio()
	 */
	@Override
	public void actualizarTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException {
		this.tramiteXMunicipio.merge(tramiteXMunicipio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * desactivarTramiteXMunicipio()
	 */
	@Override
	public void desactivarTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException {
		String sql = "Update TramiteXMunicipio txm Set txm.estado = :estado Where txm.tiposTramite.tipoTramiteId = :tipoTramiteId and txm.municipio.municipioId = :municipioId";
		Query query = this.em.createQuery(sql);
		query.setParameter("estado", "I");
		query.setParameter("tipoTramiteId", tramiteXMunicipio.getTiposTramite().getTipoTramiteId());
		query.setParameter("municipioId", tramiteXMunicipio.getMunicipio().getMunicipioId());
		
		query.executeUpdate();
	}

}
