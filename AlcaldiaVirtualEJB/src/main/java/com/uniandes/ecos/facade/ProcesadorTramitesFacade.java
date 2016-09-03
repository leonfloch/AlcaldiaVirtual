package com.uniandes.ecos.facade;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.interfaz.services.procesador.IProcesadorTramitesService;
import com.uniandes.ecos.util.NegocioException;

@Stateless
public class ProcesadorTramitesFacade implements IProcesadorTramitesFacade {

    private static Logger log = Logger.getLogger(ProcesadorTramitesFacade.class.getName());

    //-------------------------------------------------------------------------
    // INYECCIÓN DE SERVICIOS
    //-------------------------------------------------------------------------	
    /**
     * EJB encargado del ciclo de vida de los tramites
     */
    @EJB
    private IProcesadorTramitesService tramitesService;

    //-------------------------------------------------------------------------
    // METODOS
    //-------------------------------------------------------------------------
    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.IProcesadorTramitesFacade
	 * #enviarCorreo(com.uniandes.ecos.dtos.CorreoElectronicoDto)
     */
    @Override
    public void enviarCorreo(CorreoElectronicoDto correoElectronicoDto)
            throws NegocioException {
        this.tramitesService.enviarCorreo(correoElectronicoDto);

    }

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.IProcesadorTramitesFacade
	 * #cargarArchivoTramite(java.lang.Long, java.lang.String, java.lang.String, java.io.InputStream)
     */
    @Override
    public List<DocumentoTramiteDto> cargarArchivoTramite(Long tramiteId,
            String nombreArchivo, String rutaContexto, InputStream data)
            throws NegocioException {
        return this.tramitesService.cargarArchivoTramite(tramiteId, nombreArchivo, rutaContexto, data);
    }

}
