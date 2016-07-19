package com.uniandes.ecos.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.servicesInterface.IFuncionarioService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementación de los métodos de parametrización de funcionarios. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Stateless
public class FuncionarioService implements IFuncionarioService{

	/**
	 * Inyección del contexto de persistencia de la aplicación. 
	 */
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Instanciación del objeto dao para el manejo de persistencia.
	 */
	private BaseDao<UsuariosFuncionario, String> funcionarioDao;
	
	/**
	 * Inicialización de objetos del bean.
	 */
	@PostConstruct
	public void init() {
		this.funcionarioDao = new BaseDao<UsuariosFuncionario, String>(UsuariosFuncionario.class, this.em);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IFuncionarioService
	 * #registrarFuncionario(com.uniandes.ecos.entities.UsuariosFuncionario)
	 */
	@Override
	public void registrarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		this.funcionarioDao.persist(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IFuncionarioService
	 * #actualizarFuncionario(com.uniandes.ecos.entities.UsuariosFuncionario)
	 */
	@Override
	public void actualizarFuncionario(UsuariosFuncionario funcionario) throws NegocioException {
		this.funcionarioDao.merge(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IFuncionarioService
	 * #cambiarEstadofuncionario(com.uniandes.ecos.entities.UsuariosFuncionario, java.lang.String)
	 */
	@Override
	public void cambiarEstadofuncionario(UsuariosFuncionario funcionario, String estado) throws NegocioException {
		funcionario = this.funcionarioDao.findById(funcionario.getUsuario());
		funcionario.setEstado(estado);
		this.funcionarioDao.merge(funcionario);
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IFuncionarioService
	 * #obtenerFuncionarios(long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuariosFuncionario> obtenerFuncionarios(long municipioId, String tipoConsulta) throws NegocioException {
		Query query;
		List<UsuariosFuncionario> lstFuncionarios = new ArrayList<>();
		
		//Se valida el tipo de consulta a realizar.
		if (Constantes.TODOS.equals(tipoConsulta)) {
			query = this.em.createNamedQuery("UsuariosFuncionario.findByAlcaldia");			
			query.setParameter("municipioId", municipioId);
		} else{
			query = this.em.createNamedQuery("UsuariosFuncionario.findByEstado");
			query.setParameter("municipioId", municipioId);
			query.setParameter("estado", tipoConsulta);
		}
		
		//Se ejecuta la consulta
		try {
			lstFuncionarios = query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NegocioException(Constantes.ERROR, 0, "No existen funcionarios parametrizados en esta alcaldía.");
		}
		
		return lstFuncionarios;
	}

	/* (non-Javadoc)
	 * @see
	 *com.uniandes.ecos.services.IFuncionarioService
	 * #obtenerFuncionario(java.lang.String)
	 */
	@Override
	public UsuariosFuncionario obtenerFuncionario(String usuario) throws NegocioException {
		try {
			UsuariosFuncionario funcionario = this.funcionarioDao.findById(usuario);
			return funcionario;
		} catch (NoResultException e) {
			e.printStackTrace();
			throw new NegocioException(Constantes.ERROR, 0, "No existe ningún funcionario que correspoda al usuario ingresado.");
		}
	}
}
