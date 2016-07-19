package com.uniandes.ecos.servicesInterface;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.util.NegocioException;

/**
 * Interface que expone los m�todos de parametrizaci�n de funcionarios. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Local
public interface IFuncionarioService {

	/**
	 * Registra en BD la entidad enviada como par�metro. 
	 * 
	 * @param funcionario
	 * @throws NegocioException
	 */
	void registrarFuncionario(UsuariosFuncionario funcionario) throws NegocioException;
	
	/**
	 * Actualiza la informaci�n del funcionario enviado como par�metro.
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
	 * Retorna la lista de funcionarios pertenencientes a una alcald�a, 
	 * dependiendo del tipo de consulta: todos 'T', activos 'A', inactivos 'I'  
	 * 
	 * @param municipioId
	 * @return
	 * @throws NegocioException
	 */
	List<UsuariosFuncionario> obtenerFuncionarios(long municipioId, String tipoConsulta) throws NegocioException;
	
	/**
	 * Retorna la informaci�n del funcinario a  partir de su usuario. 
	 * 
	 * @param usuario
	 * @return
	 * @throws NegocioException
	 */
	UsuariosFuncionario obtenerFuncionario(String usuario) throws NegocioException; 
}
