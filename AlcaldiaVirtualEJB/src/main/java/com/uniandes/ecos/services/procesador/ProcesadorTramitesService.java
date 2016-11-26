/**
 *
 */
package com.uniandes.ecos.services.procesador;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.DocumentoTramite;
import com.uniandes.ecos.entities.FormularioTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.interfaz.services.procesador.ICorreosService;
import com.uniandes.ecos.interfaz.services.procesador.IDocumentosService;
import com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de llevar el ciclo de vida de los tramites en curso
 *
 * @author 80221940
 *
 */
@Stateless
public class ProcesadorTramitesService implements IProcesadorTramitesService {

	// -------------------------------------------------------------------------
	// INYECCI�N DE SERVICIOS
	// -------------------------------------------------------------------------
	/**
	 * Inyecci�n del contexto de persistencia de la aplicaci�n.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Administracion de documentos
	 */
	@EJB
	private IDocumentosService documentosService;

	/**
	 * envio de correos
	 */
	@EJB
	private ICorreosService correosService;

	/**
	 * Instanciaci�n del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<Tramite, Long> tramiteDao;

	// -------------------------------------------------------------------------
	// METODOS
	// -------------------------------------------------------------------------

	@PostConstruct
	public void init() {
		this.tramiteDao = new BaseDao<Tramite, Long>(Tramite.class, this.em);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
	 * enviarCorreo(com.uniandes.ecos.dtos.CorreoElectronicoDto)
	 */
	@Override
	public void enviarCorreo(CorreoElectronicoDto correoElectronicoDto) throws NegocioException {
		this.correosService.enviarCorreo(correoElectronicoDto);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService
	 * #cargarArchivoTramite(java.lang.Long, java.lang.String,
	 * java.io.InputStream)
	 */
	@Override
	public DocumentoTramiteDto cargarArchivoTramite(Long tramiteId, String nombreArchivo, InputStream data)
			throws NegocioException {
		return this.documentosService.cargarArchivoTramite(tramiteId, nombreArchivo, data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
	 * crearTramite(com.uniandes.ecos.entities.Tramite)
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void crearTramite(Tramite tramite) throws NegocioException {
		tramiteDao.persist(tramite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
	 * obtenerTramites(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> obtenerTramites(long municipioId) throws NegocioException {
		List<Tramite> lstTramites = new ArrayList<>();

        Query query = this.em.createNamedQuery("Tramite.findByMunicipio");
        query.setParameter("municipioId", municipioId);

        //Se ejecuta la consulta
        try {
        	lstTramites = query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            throw new NegocioException(Constantes.INFO, 0, "No existen tr�mites a procesar en el momento.");
        }
        
		return lstTramites;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
	 * obtenerTramite(long)
	 */
	@Override
	public Tramite obtenerTramite(long tramiteId) throws NegocioException {
		return tramiteDao.findById(tramiteId);
	}

	/*
     * (non-Javadoc)
     * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
     * cambiarEstadoTramite(long, java.lang.String)
     */
	@Override
	public void cambiarEstadoTramite(long tramiteId, String estado) throws NegocioException {
		Tramite tramite = tramiteDao.findById(tramiteId);
		tramite.setEstado(estado);
		tramiteDao.merge(tramite);
		this.em.flush();
	}
	
	/*
     * (non-Javadoc)
     * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
     * buscarDocumentosPorTramite(long)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoTramite> buscarDocumentosPorTramite(long tramiteId) throws NegocioException {
		String sql = "Select dt From DocumentoTramite dt Where dt.tramite.tramiteId = :tramiteId";
		Query query = this.em.createQuery(sql);
		query.setParameter("tramiteId", tramiteId);
		
		return query.getResultList();
	}

	/*
     * (non-Javadoc)
     * @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#* @see com.uniandes.ecos.interfaz.services.procesador.
	 * IProcesadorTramitesService#
     * buscarFormulariosPorTramite(long)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<FormularioTramite> buscarFormulariosPorTramite(long tramiteId) throws NegocioException {
		String sql = "Select ft From FormularioTramite ft Where ft.tramite.tramiteId = :tramiteId";
		Query query = this.em.createQuery(sql);
		query.setParameter("tramiteId", tramiteId);
		
		return query.getResultList();
	}

	@Override
	public void actualizarTramite(Tramite tramite) throws NegocioException {
		tramiteDao.merge(tramite);
	}

}
