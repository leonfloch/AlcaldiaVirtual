package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.facadeInterface.IParametrizacionFacade;


/**
 * Managed bean para manejo de componentes de pantallas de administración de funcionarios. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@ViewScoped
@ManagedBean
public class FuncionariosMB extends BaseMBean {
	
	/** Inyección de dependecia con fachada de parametrización. */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	
	/** Listas de la pantalla. */
	private List<UsuariosFuncionario> lstFuncionarios;
	
	/** Entidades para manejo de funcionarios. */
	private UsuariosFuncionario funcionarioEntity;
	private Persona personaEntity;
	
	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		funcionarioEntity = new UsuariosFuncionario();
		lstFuncionarios = new ArrayList<UsuariosFuncionario>();
	}
}
