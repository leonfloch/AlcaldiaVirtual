package com.uniandes.ecos.interfaz.facade;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.util.NegocioException;

/**
 * Fachada encargada de toda la parametrizacion de tramites de la
 * aplicacion
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

}
