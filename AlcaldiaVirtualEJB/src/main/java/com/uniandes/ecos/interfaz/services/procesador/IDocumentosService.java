/**
 * 
 */
package com.uniandes.ecos.interfaz.services.procesador;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado del manejo de los documentos que se cargan
 * y descargan en el sistema.
 * @author 80221940
 *
 */
@Local
public interface IDocumentosService {
	
	/**
	 * Se encarga de cargar un archivo
	 * @param tramiteId
	 * @param nombreArchivo
	 * @param rutaContexto
	 * @param data
	 * @return
	 * @throws NegocioException
	 */
	List<DocumentoTramiteDto> cargarArchivoTramite(Long tramiteId, String nombreArchivo, 
			String rutaContexto, InputStream data) throws NegocioException;

}
