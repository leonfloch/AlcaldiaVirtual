/**
 * 
 */
package com.uniandes.ecos.interfaz.services.procesador;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.DocumentoRequerido;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado del manejo de los documentos que se cargan y descargan en
 * el sistema.
 * 
 * @author 80221940
 *
 */
@Local
public interface IDocumentosService {

	/**
	 * Se encarga de cargar un archivo
	 * 
	 * @param tramiteId
	 * @param nombreArchivo
	 * @param rutaContexto
	 * @param data
	 * @return
	 * @throws NegocioException
	 */
	 DocumentoTramiteDto cargarArchivoTramite(Long tramiteId, String nombreArchivo, InputStream data)
			throws NegocioException;
	 
	 /**
	  * Retorna todos los documentos de un tramite
	  * @param idTramite
	  * @return
	  * @throws NegocioException
	  */
	 List<DocumentoTramiteDto> obtenerArchivosTramite(Long idTramite) throws NegocioException;
	 
	 /**
	  * Se encarga de subir un archivo a la carpeta de documentos requeridos
	  * @param nombreArchivo
	  * @param data
	  * @throws NegocioException
	  * @return DocumentoRequerido
	  */
	 DocumentoRequerido cargarDocumentoRequerido(String nombreArchivo, InputStream data) throws NegocioException; 

}
