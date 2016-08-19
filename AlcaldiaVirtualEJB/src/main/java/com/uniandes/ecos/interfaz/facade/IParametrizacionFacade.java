package com.uniandes.ecos.interfaz.facade;

import java.util.List;

import javax.ejb.Local;

import com.uniandes.ecos.entities.Funcionalidad;
import com.uniandes.ecos.entities.PermisoXRol;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.util.NegocioException;

/**
 * Interface que expone los m�todos del m�dulo de parametrizaci�n. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Local
public interface IParametrizacionFacade {

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
	 * Actualiza en BD la persona ingresada por par�metro. 
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
	
	/**
	 * obtiene un ciudadano por el id
	 * @param numIdentificacion
	 * @return
	 * @throws NegocioException
	 */
	UsuariosCiudadano obtenerCiudadano(long numIdentificacion) throws NegocioException;
	
	/**
	 * consulta la lista de roles del sistema
	 * @return
	 * @throws NegocioException
	 */
	List<Rol> obtenerRoles() throws NegocioException;
	
	/**
	 * actualiza el rol enviado por paramtetro
	 * @param rol
	 * @throws NegocioException
	 */
	void actualizarRol(Rol rol) throws NegocioException;
	
	/**
	 * Crea un nuevo rol en el sistema
	 * @param rol
	 * @throws NegocioException
	 */
	void crearRol(Rol rol) throws NegocioException;
	
	/**
	 * consulta las funcionabilidades del sistema
	 * @return
	 * @throws NegocioException
	 */
	List<Funcionalidad> obtenerFuncionalidades()  throws NegocioException;
	
	/**
	 * consulta la tabla permisoXRol
	 * @return
	 * @throws NegocioException
	 */
	List<PermisoXRol> obtenerPermisosXRol()  throws NegocioException;
	
	
}
