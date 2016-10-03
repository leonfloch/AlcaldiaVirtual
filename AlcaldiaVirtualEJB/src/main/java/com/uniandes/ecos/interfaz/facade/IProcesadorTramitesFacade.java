package com.uniandes.ecos.interfaz.facade;



import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.util.NegocioException;



/**
 * Fachada encargada de realizar el procesos de los tramites
 * @author 80221940
 *
 */
@Local
public interface IProcesadorTramitesFacade {
	
	/**
	 * Envia correo electronico
	 * @param correoElectronicoDto
	 * @throws NegocioException
	 */
	void enviarCorreo(CorreoElectronicoDto correoElectronicoDto) throws NegocioException;
	
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
	
	/**
	 * Crea el nuevo tramite enviado por parametro
	 * @param tramite tramite que desea crear el ciudadano
	 * @throws NegocioException
	 */
	void crearTramite(Tramite tramite) throws NegocioException;
	
	
}
