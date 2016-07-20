package com.uniandes.ecos.servicesInterface;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.util.NegocioException;

/**
 * Interface que expone los m�todos de parametrizaci�n de personas. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Local
public interface IPersonaService {
	
	/**
	 * Registra en BD la persona ingresada por par�metro. 
	 * 
	 * @param persona
	 * @throws NegocioException
	 */
	void registrarPersona(Persona persona) throws NegocioException;

	/**
	 * Retorna la informaci�n de la persona a trav�s de su n�mero de identificaci�n. 
	 * 
	 * @param numIdentificacion
	 * @return
	 * @throws NegocioException
	 */
	Persona obtenerPersona(long numIdentificacion) throws NegocioException; 
	
	/**
	 * Actualiza el resgitro de la persona ingresada como par�metro.
	 * 
	 * @param persona
	 * @throws NegocioException
	 */
	void actualizarPersona(Persona persona) throws NegocioException;
}
