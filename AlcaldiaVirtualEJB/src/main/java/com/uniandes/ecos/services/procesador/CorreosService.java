/**
 * 
 */
package com.uniandes.ecos.services.procesador;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.interfaz.services.procesador.ICorreosService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.JavaMailSender;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementacion Servicio encargado de el envio de correos electronicos
 * @author 80221940
 *
 */
@Stateless
public class CorreosService implements ICorreosService {
	
	private static Logger log = Logger.getLogger(CorreosService.class.getName());
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.procesador.ICorreosService#
	 * enviarCorreo(com.uniandes.ecos.dtos.CorreoElectronicoDto)
	 */
	@Override
	public void enviarCorreo(CorreoElectronicoDto correoElectronicoDto) throws NegocioException {
		try {
			JavaMailSender.enviarCorreo(correoElectronicoDto);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.WARNING, e.getMessage());
			throw new NegocioException('E', Constantes.CODIGO_ERROR_ENVIO_CORREO,
					"Se ha presentado un error al enviar el correo");
		}
		
	}

}
