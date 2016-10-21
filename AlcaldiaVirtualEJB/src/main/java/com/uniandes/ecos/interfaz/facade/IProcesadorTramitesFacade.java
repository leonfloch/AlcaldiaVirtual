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
	 * @param data
	 * @return
	 * @throws NegocioException
	 */
	DocumentoTramiteDto cargarArchivoTramite(Long tramiteId, String nombreArchivo, 
			InputStream data) throws NegocioException;
	
	/**
	 * Crea el nuevo tramite enviado por parametro
	 * @param tramite tramite que desea crear el ciudadano
	 * @throws NegocioException
	 */
	void crearTramite(Tramite tramite) throws NegocioException;
	
	/**
	 * Obtiene la lista de trámites a ser procesados.
	 * 
	 * @param municipioId
	 * @return
	 * @throws NegocioException
	 */
	List<Tramite> obtenerTramites(long municipioId) throws NegocioException;
	
	/**
	 * Obtiene un trámite específico.
	 * 
	 * @param tramiteId
	 * @return
	 * @throws NegocioException
	 */
	Tramite obtenerTramite(long tramiteId) throws NegocioException;
 	
	/**
	 * Cambia el estado de un trámite específico. 
	 * 
	 * @param tramiteId
	 * @param estado
	 * @throws NegocioException
	 */
	void cambiarEstadoTramite(long tramiteId, String estado) throws NegocioException;
}
