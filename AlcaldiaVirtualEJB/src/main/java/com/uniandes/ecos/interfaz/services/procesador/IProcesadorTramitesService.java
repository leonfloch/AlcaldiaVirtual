/**
 * 
 */
package com.uniandes.ecos.interfaz.services.procesador;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.DocumentoTramite;
import com.uniandes.ecos.entities.FormularioTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de llevar el ciclo de vida de los
 * tramites en curso
 * @author 80221940
 *
 */
@Local
public interface IProcesadorTramitesService {
	
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
	 * Obtiene la lista de trï¿½mites a ser procesados.
	 * 
	 * @param municipioId
	 * @return
	 * @throws NegocioException
	 */
	List<Tramite> obtenerTramites(long municipioId) throws NegocioException;
	
	
	/**
	 * Obtiene la lista de tramites de un ciudadano
	 * @param usuario
	 * @param municipioId
	 * @return
	 * @throws NegocioException
	 */
	List<Tramite> obtenerTramitesCiudadano(String usuario, long municipioId) throws NegocioException;
	
	/**
	 * Obtiene un trï¿½mite especï¿½fico.
	 * 
	 * @param tramiteId
	 * @return
	 * @throws NegocioException
	 */
	Tramite obtenerTramite(long tramiteId) throws NegocioException;
	
	/**
	 * Cambia el estado de un trï¿½mite especï¿½fico. 
	 * 
	 * @param tramiteId
	 * @param estado
	 * @throws NegocioException
	 */
	void cambiarEstadoTramite(long tramiteId, String estado) throws NegocioException;
	
	/**
	 * Busca todos los documentos cargados segun el tramite
	 * @param tramiteId
	 * @return
	 * @throws NegocioException
	 */
	List<DocumentoTramite> buscarDocumentosPorTramite(long tramiteId) throws NegocioException;
	
	/**
	 * Busca todos los formularios diligenciados segun el tramite
	 * @param tramiteId
	 * @return
	 * @throws NegocioException
	 */
	List<FormularioTramite> buscarFormulariosPorTramite(long tramiteId) throws NegocioException;
	
	/**
	 * Actualiza un trámite específico. 
	 * 
	 * @param tramite
	 * @throws NegocioException
	 */
	void actualizarTramite(Tramite tramite) throws NegocioException;
}
