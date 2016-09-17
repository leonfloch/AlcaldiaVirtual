/**
 * 
 */
package com.uniandes.ecos.interfaz.services.parametrizacion;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.TipoTramite;
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
	 * Busca los tipos de tramite por nombre
	 * @param nombre
	 * @return
	 * @throws NegocioException
	 */
	public List<TipoTramite> obtenerListaTramites(String nombre)throws NegocioException;
}
