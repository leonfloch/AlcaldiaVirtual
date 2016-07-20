package com.uniandes.ecos.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.facadeInterface.IParametrizacionFacade;
import com.uniandes.ecos.servicesInterface.IFuncionarioService;
import com.uniandes.ecos.servicesInterface.IPersonaService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementación de los métodos del módulo de parametrización. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Stateless
public class ParametrizacionFacade implements IParametrizacionFacade{
	
	/**
	 * Inyección de dependencia con servicios de parametrización.
	 */
	@EJB
	private IFuncionarioService funcionarioService; 
	@EJB 
	private IPersonaService personaService;

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #registrarFuncionario(com.uniandes.ecos.entities.UsuariosFuncionario)
	 */
	@Override
	public void registrarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		funcionarioService.registrarFuncionario(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #actualizarFuncionario(com.uniandes.ecos.entities.UsuariosFuncionario)
	 */
	@Override
	public void actualizarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		funcionarioService.actualizarFuncionario(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #cambiarEstadofuncionario(com.uniandes.ecos.entities.UsuariosFuncionario, java.lang.String)
	 */
	@Override
	public void cambiarEstadofuncionario(UsuariosFuncionario funcionario, String estado) throws NegocioException {
		funcionarioService.cambiarEstadofuncionario(funcionario, estado);
	}
	
	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #obtenerFuncionarios(long, java.lang.String)
	 */
	@Override
	public List<UsuariosFuncionario> obtenerFuncionarios(long municipioId, String tipoConsulta)
			throws NegocioException {
		return funcionarioService.obtenerFuncionarios(municipioId, tipoConsulta);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #obtenerFuncionario(java.lang.String)
	 */
	@Override
	public UsuariosFuncionario obtenerFuncionario(String usuario) throws NegocioException {
		return funcionarioService.obtenerFuncionario(usuario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #registrarPersona(com.uniandes.ecos.entities.Persona)
	 */
	@Override
	public void registrarPersona(Persona persona) throws NegocioException {
		personaService.registrarPersona(persona);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #obtenerPersona(long)
	 */
	@Override
	public Persona obtenerPersona(long numIdentificacion) throws NegocioException {
		return personaService.obtenerPersona(numIdentificacion);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IParametrizacionFacade
	 * #actualizarPersona(com.uniandes.ecos.entities.Persona)
	 */
	@Override
	public void actualizarPersona(Persona persona) throws NegocioException {
		personaService.actualizarPersona(persona);
	}

}
