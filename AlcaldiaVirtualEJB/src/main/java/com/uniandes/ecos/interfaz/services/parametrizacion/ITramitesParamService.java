/**
 * 
 */
package com.uniandes.ecos.interfaz.services.parametrizacion;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de crear los tipo tramites
 * @author 80221940
 *
 */
@Local
public interface ITramitesParamService {
	/**
	 * Registra en BD la entidad enviada como parámetro.
	 * 
	 * @param tipoTramite
	 * @throws NegocioException
	 */
	void crearTipoTramite(TipoTramite tipoTramite) throws NegocioException;
	
	/**
	 * Actualiza en BD la entidad enviada como parámetro.
	 * 
	 * @param tipoTramite
	 * @throws NegocioException
	 */
	void actualizarTipoTramite(TipoTramite tipoTramite) throws NegocioException;
	
	/**
	 * Busca los tipos de tramite por nombre
	 * @param nombre
	 * @return
	 * @throws NegocioException
	 */
	public List<TipoTramite> obtenerListaTiposTramites(String nombre)throws NegocioException;
	
	/**
	 * Retorna una lista con los municipios almacenados en el sistema.
	 * @return
	 * @throws NegocioException
	 */
	public List<Municipio> obtenerListaMunicipios() throws NegocioException;
	
	/**
	 * Retorna una lista de municipios asociado a un tipo de tramite
	 * @param tipoTramiteId
	 * @return
	 * @throws NegocioException
	 */
	public List<Municipio> obtenerListaMunicipiosXTipoTramite(long tipoTramiteId) throws NegocioException;
	
	/**
	 * Registra en BD la entidad enviada como parámetro.
	 * 
	 * @param tramiteXMunicipio
	 * @throws NegocioException
	 */
	void crearTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException;
	
	/**
	 * Actualiza en BD la entidad enviada como parámetro.
	 * 
	 * @param tramiteXMunicipio
	 * @throws NegocioException
	 */
	void actualizarTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException;
	
	/**
	 * Cambia el estado del Tramite X Municipio a inactivo.
	 * 
	 * @param tramiteXMunicipio
	 * @throws NegocioException
	 */
	void desactivarTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException;
}
