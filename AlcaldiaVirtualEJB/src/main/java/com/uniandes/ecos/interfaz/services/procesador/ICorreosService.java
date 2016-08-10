/**
 * 
 */
package com.uniandes.ecos.interfaz.services.procesador;

import javax.ejb.Local;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.util.NegocioException;

/**
 * Servicio encargado de el envio de correos electronicos
 * @author 80221940
 *
 */
@Local
public interface ICorreosService {
	
	/**
	 * Envia correo electronico
	 * @param correoElectronicoDto
	 * @throws NegocioException
	 */
	void enviarCorreo(CorreoElectronicoDto correoElectronicoDto) throws NegocioException;

}
