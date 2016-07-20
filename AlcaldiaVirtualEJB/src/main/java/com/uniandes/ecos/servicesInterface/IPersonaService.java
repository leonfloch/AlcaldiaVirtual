package com.uniandes.ecos.servicesInterface;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.util.NegocioException;

/**
 * Interface que expone los métodos de parametrización de personas. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Local
public interface IPersonaService {
	
	/**
	 * Registra en BD la persona ingresada por parámetro. 
	 * 
	 * @param persona
	 * @throws NegocioException
	 */
	void registrarPersona(Persona persona) throws NegocioException;

	/**
	 * Retorna la información de la persona a través de su número de identificación. 
	 * 
	 * @param numIdentificacion
	 * @return
	 * @throws NegocioException
	 */
	Persona obtenerPersona(long numIdentificacion) throws NegocioException; 
	
	/**
	 * Actualiza el resgitro de la persona ingresada como parámetro.
	 * 
	 * @param persona
	 * @throws NegocioException
	 */
	void actualizarPersona(Persona persona) throws NegocioException;
}
