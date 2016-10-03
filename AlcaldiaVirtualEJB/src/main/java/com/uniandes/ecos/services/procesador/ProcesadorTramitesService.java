/**
 *
 */
package com.uniandes.ecos.services.procesador;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.interfaz.services.procesador.ICorreosService;
import com.uniandes.ecos.interfaz.services.procesador.IDocumentosService;
import com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de llevar el ciclo de vida de los tramites en curso
 *
 * @author 80221940
 *
 */
@Stateless
public class ProcesadorTramitesService implements IProcesadorTramitesService {

    //-------------------------------------------------------------------------
    // INYECCIÓN DE SERVICIOS
    //-------------------------------------------------------------------------
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

    //-------------------------------------------------------------------------
    // METODOS
    //-------------------------------------------------------------------------
    
    @PostConstruct
    public void init() {
    	this.tramiteDao = new BaseDao<Tramite, Long>(Tramite.class, this.em);
    }
    

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService#
	 * enviarCorreo(com.uniandes.ecos.dtos.CorreoElectronicoDto)
     */
    @Override
    public void enviarCorreo(CorreoElectronicoDto correoElectronicoDto)
            throws NegocioException {
        this.correosService.enviarCorreo(correoElectronicoDto);

    }

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService
	 * #cargarArchivoTramite(java.lang.Long, java.lang.String, java.lang.String, java.io.InputStream)
     */
    @Override
    public List<DocumentoTramiteDto> cargarArchivoTramite(Long tramiteId,
            String nombreArchivo, String rutaContexto, InputStream data)
            throws NegocioException {
        return this.documentosService.cargarArchivoTramite(tramiteId, nombreArchivo,
                rutaContexto, data);
    }

    /*
     * (non-Javadoc)
     * @see com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService#
     * crearTramite(com.uniandes.ecos.entities.Tramite)
     */
	@Override
	public void crearTramite(Tramite tramite) throws NegocioException {
		tramiteDao.persist(tramite);		
	}

}
