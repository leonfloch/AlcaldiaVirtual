/**
 *
 */
package com.uniandes.ecos.services.procesador;

import java.io.InputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
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
     * Administracion de documentos
     */
    @EJB
    private IDocumentosService documentosService;

    /**
     * envio de correos
     */
    @EJB
    private ICorreosService correosService;

    //-------------------------------------------------------------------------
    // METODOS
    //-------------------------------------------------------------------------

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

}
