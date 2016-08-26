/**
 * 
 */
package com.uniandes.ecos.interfaz.services.parametrizacion;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de crear los formularios dinamicos
 * para los tramites.
 * @author Juan Albarrac�n.
 *
 */
@Local
public interface IFormulariosParamService {

	/**
	 * Registra en BD la entidad enviada como par�metro. 
	 * 
	 * @param formulario
	 * @throws NegocioException
	 */
	void crearFormulario(Formulario formulario) throws NegocioException;
	
	/**
	 * Actualiza la informaci�n del formulario enviado como par�metro.
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
