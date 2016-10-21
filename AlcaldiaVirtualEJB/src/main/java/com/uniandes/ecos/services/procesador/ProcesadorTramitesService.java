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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.TipoTramite;
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
	// INYECCIÓN DE SERVICIOS
	// -------------------------------------------------------------------------
	/**
	 * Inyecciï¿½n del contexto de persistencia de la aplicaciï¿½n.
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
	 * Instanciación del objeto dao para el manejo de persistencia.
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
            throw new NegocioException(Constantes.INFO, 0, "No existen trámites a procesar en el momento.");
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
     * @see com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade#
     * cambiarEstadoTramite(long, java.lang.String)
     */
	@Override
	public void cambiarEstadoTramite(long tramiteId, String estado) throws NegocioException {
		Tramite tramite = tramiteDao.findById(tramiteId);
		tramite.setEstado(estado);
		tramiteDao.merge(tramite);
		this.em.flush();
	}

}
