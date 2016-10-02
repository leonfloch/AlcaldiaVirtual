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
import com.uniandes.ecos.entities.DocsXTipoTramite;
import com.uniandes.ecos.entities.DocumentoRequerido;
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
	private BaseDao<TramiteXMunicipio, Long> tramiteXMunicipioDao;
	
	/**
	 * Instanciación del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<DocsXTipoTramite, Long> documentosXTramiteDao;

	/**
	 * Inicialización de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.tipoTramiteDao = new BaseDao<TipoTramite, Long>(TipoTramite.class, this.em);
		this.tramiteXMunicipioDao = new BaseDao<TramiteXMunicipio, Long>(TramiteXMunicipio.class, this.em);
		this.documentosXTramiteDao = new BaseDao<DocsXTipoTramite, Long>(DocsXTipoTramite.class, this.em);
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
		this.tramiteXMunicipioDao.persist(tramiteXMunicipio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * actualizarTramiteXMunicipio()
	 */
	@Override
	public void actualizarTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException {
		this.tramiteXMunicipioDao.merge(tramiteXMunicipio);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * desactivarTramiteXMunicipio()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TramiteXMunicipio> obtenerTramiteXMunicipioXTipoTramiteId(long tipoTramiteId) throws NegocioException {
		String sql = "Select txm From TramiteXMunicipio txm Where txm.tiposTramite.tipoTramiteId = :tipoTramiteId";
		Query query = this.em.createQuery(sql);
		query.setParameter("tipoTramiteId", tipoTramiteId);
		
		return query.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerListaDocumentos()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoRequerido> obtenerListaDocumentos() throws NegocioException {
		return this.em.createNamedQuery("DocumentoRequerido.findAll").getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerListaDocumentosXTipoTramite()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoRequerido> obtenerListaDocumentosXTipoTramite(long tipoTramiteId) throws NegocioException {
		String sql = "Select dxtt.documentosRequerido From DocsXTipoTramite dxtt Where dxtt.tiposTramite.tipoTramiteId = :tipoTramiteId and dxtt.estado = :estado";
		Query query = this.em.createQuery(sql);
		query.setParameter("tipoTramiteId", tipoTramiteId);
		query.setParameter("estado", "A");
		return query.getResultList();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * actualizarDocumentoXTramite()
	 */
	@Override
	public void actualizarDocumentoXTramite(DocsXTipoTramite docsXTipoTramite) throws NegocioException {
		this.documentosXTramiteDao.merge(docsXTipoTramite);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerDocumentosXTramiteXTipoTramiteId()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DocsXTipoTramite> obtenerDocumentosXTramiteXTipoTramiteId(long tipoTramiteId) throws NegocioException {
		String sql = "Select dxtt From DocsXTipoTramite dxtt Where dxtt.tiposTramite.tipoTramiteId = :tipoTramiteId";
		Query query = this.em.createQuery(sql);
		query.setParameter("tipoTramiteId", tipoTramiteId);
		
		return query.getResultList();
	}
}
