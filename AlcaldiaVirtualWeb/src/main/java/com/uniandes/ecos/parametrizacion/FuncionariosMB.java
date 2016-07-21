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
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;


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

	/**
	 * Serial de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/** Inyección de dependecia con fachada de parametrización. */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;

	/** Listas de la pantalla. */
	private List<UsuariosFuncionario> lstFuncionarios;

	/** Entidades para manejo de funcionarios. */
	private UsuariosFuncionario funcionarioEntity;
	private Persona personaEntity;

	/** Variables para control de componentes en pantalla. */
	private String usuario;
	private long municipioId;
	private boolean existePersona;

	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		funcionarioEntity = new UsuariosFuncionario();
		lstFuncionarios = new ArrayList<UsuariosFuncionario>();
		for (int i = 0; i < 5; i++) {
			UsuariosFuncionario usu = new UsuariosFuncionario();
			usu.setUsuario(""+i);
			usu.setContrasenia("ñlsdkñlskdfñlsk");
			usu.setEstado(Constantes.ACTIVO);
			lstFuncionarios.add(usu);
		}
	}

	/**
	 * COnsulta la lista de funcionarios parametrizados en la alcaldía.
	 */
	public void buscar(){
		try {
			if (this.usuario != null) {
				this.funcionarioEntity = iParametrizacionFacade.obtenerFuncionario(usuario);
			} else {
				this.lstFuncionarios = iParametrizacionFacade.obtenerFuncionarios(municipioId,
						Constantes.TODOS);
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo().charAt(0), e.getMensaje());
		}
	}

	/**
	 * Prepara la forma para la creación o modificación de un funcionario.
	 * @param esModificaion
	 */
	public void prepararForma(){
		this.funcionarioEntity = new UsuariosFuncionario();
		this.funcionarioEntity.setEstado(Constantes.ACTIVO);
		this.funcionarioEntity.setRolId(Constantes.ROL_FUNCIONARIO);
		this.funcionarioEntity.setMunicipioId(this.municipioId);
		this.personaEntity = new Persona();
	}

	/**
	 * Verifica a través del número de identificación ingresado
	 * si la persona ya fue creada en la base de datos. 
	 */
	public void buscarPersona(){
		Persona personaAux = null;
		try {
			personaAux = iParametrizacionFacade.obtenerPersona(this.personaEntity.getNumIdentificacion());
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo().charAt(0), e.getMensaje());
		}

		//Si ya existe la persona se mapea al objeto en pantalla.
		if (personaAux != null) {
			this.existePersona = true;
			this.personaEntity.setNombres(personaAux.getNombres());
			this.personaEntity.setApellidos(personaAux.getApellidos());
			this.personaEntity.setDireccion(personaAux.getDireccion());
			this.personaEntity.setEmail(personaAux.getEmail());
			this.personaEntity.setTelefono(personaAux.getTelefono());
			this.personaEntity.setTipoIdentificacion(personaAux.getTipoIdentificacion());
		} else{
			this.existePersona = false;
		}
	}

	/**
	 * Registra el nuevo usuario funcionario en la base de datos.
	 */
	public void registrar(){
		this.funcionarioEntity.setUsuario(String.valueOf(this.personaEntity.getNumIdentificacion()));
		this.funcionarioEntity.setPersona(this.personaEntity);

		//Se invoca el método de registro
		try {
			if (this.existePersona) {
				iParametrizacionFacade.actualizarPersona(this.personaEntity);
			} else{
				iParametrizacionFacade.registrarPersona(this.personaEntity);
			}
			iParametrizacionFacade.registrarFuncionario(this.funcionarioEntity);
			this.adicionarMensaje(Constantes.INFO.charAt(0), "Se registró el funcionario exitosamente.");
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo().charAt(0), e.getMensaje());
		}

	}
	
	/**
	 * Ejecuta el método encargado de la actualizaciónd e los datos del funcionario.
	 */
	public void actualizar(){
		try {
			iParametrizacionFacade.actualizarPersona(this.personaEntity);
			iParametrizacionFacade.actualizarFuncionario(this.funcionarioEntity);
			this.adicionarMensaje(Constantes.INFO.charAt(0), "Se actualizó la información de manera exitosa.");
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo().charAt(0), e.getMensaje());
		}
	}
	
	/**
	 * Limpia los valores ingresados en la pantalla.
	 */
	public void limpiarPantalla(){
		this.funcionarioEntity = new UsuariosFuncionario();
		this.personaEntity = new Persona();
		this.lstFuncionarios.clear();
		this.usuario = null;
	}

	/**
	 * @return the lstFuncionarios
	 */
	public List<UsuariosFuncionario> getLstFuncionarios() {
		return lstFuncionarios;
	}

	/**
	 * @param lstFuncionarios the lstFuncionarios to set
	 */
	public void setLstFuncionarios(List<UsuariosFuncionario> lstFuncionarios) {
		this.lstFuncionarios = lstFuncionarios;
	}

	/**
	 * @return the funcionarioEntity
	 */
	public UsuariosFuncionario getFuncionarioEntity() {
		return funcionarioEntity;
	}

	/**
	 * @param funcionarioEntity the funcionarioEntity to set
	 */
	public void setFuncionarioEntity(UsuariosFuncionario funcionarioEntity) {
		this.funcionarioEntity = funcionarioEntity;
	}

	/**
	 * @return the personaEntity
	 */
	public Persona getPersonaEntity() {
		return personaEntity;
	}

	/**
	 * @param personaEntity the personaEntity to set
	 */
	public void setPersonaEntity(Persona personaEntity) {
		this.personaEntity = personaEntity;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
