package com.uniandes.ecos.interfaz.facade;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.util.NegocioException;

/**
 * Fachada encargada de toda la parametrizacion de tramites de la aplicacion
 * 
 * @author Juan Albarracín.
 *
 */
@Local
public interface IParamTramitesFacade {

	/**
	 * Registra en BD la entidad enviada como parámetro.
	 * 
	 * @param formulario
	 * @throws NegocioException
	 */
	void crearFormulario(Formulario formulario) throws NegocioException;

	/**
	 * Actualiza en BD la entidad enviada como parámetro.
	 * 
	 * @param formulario
	 * @throws NegocioException
	 */
	void actualizarFormulario(Formulario formulario) throws NegocioException;

	/**
	 * Obtiene los formularios parametrizados en el sistema.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	List<Formulario> obtenerFormularios(String nombre) throws NegocioException;

	/**
	 * Obtiene el formulario a partir del Id.
	 * 
	 * @param formularioId
	 * @return
	 * @throws NegocioException
	 */
	Formulario obtenerFormulario(long formularioId) throws NegocioException;

	/**
	 * Obtiene los tipos de campo parametrizados en el sistema.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	List<TipoCampo> obtenerTiposCampoForm() throws NegocioException;

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
	 * 
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
