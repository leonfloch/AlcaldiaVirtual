package com.uniandes.ecos.facadeInterface;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.util.NegocioException;

/**
 * Interface que expone los métodos del módulo de parametrización. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Local
public interface IParametrizacionFacade {

	/**
	 * Registra en BD la entidad enviada como parámetro. 
	 * 
	 * @param funcionario
	 * @throws NegocioException
	 */
	void registrarFuncionario(UsuariosFuncionario funcionario) throws NegocioException;
	
	/**
	 * Actualiza la información del funcionario enviado como parámetro.
	 * 
	 * @param funcionario
	 * @throws NegocioException
	 */
	void actualizarFuncionario(UsuariosFuncionario funcionario) throws NegocioException;
	
	/**
	 * Cambia el estado del funcionario a inactivo 'I' o activo 'A'.
	 * 
	 * @param funcionario
	 * @param estado
	 * @throws NegocioException
	 */
	void cambiarEstadofuncionario(UsuariosFuncionario funcionario, String estado) throws NegocioException;
	
	/**
	 * Retorna la lista de funcionarios pertenencientes a una alcaldía, 
	 * dependiendo del tipo de consulta: todos 'T', activos 'A', inactivos 'I'  
	 * 
	 * @param municipioId
	 * @return
	 * @throws NegocioException
	 */
	List<UsuariosFuncionario> obtenerFuncionarios(long municipioId, String tipoConsulta) throws NegocioException;
	
	/**
	 * Retorna la información del funcinario a  partir de su usuario. 
	 * 
	 * @param usuario
	 * @return
	 * @throws NegocioException
	 */
	UsuariosFuncionario obtenerFuncionario(String usuario) throws NegocioException;
	
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
	 * Actualiza en BD la persona ingresada por parámetro. 
	 * 
	 * @param persona
	 * @throws NegocioException
	 */
	void actualizarPersona(Persona persona) throws NegocioException;
	
	/**
	 * Crea un nuevo ciudadano en el sistema
	 * @param ciudadano
	 * @throws NegocioException
	 */
	void registrarCiudadano(UsuariosCiudadano ciudadano) throws NegocioException;
}
