package com.uniandes.ecos.parametrizacion;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;


/**
 * Managed bean para manejo de componentes de pantallas de administraciï¿½n de funcionarios. 
 * 
 * @author Juan Albarracï¿½n
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

	/** Inyecciï¿½n de dependecia con fachada de parametrizaciï¿½n. */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	private UsuarioSesion usuarioSesion;

	/** Listas de la pantalla. */
	private List<UsuariosFuncionario> lstFuncionarios;

	/** Entidades para manejo de funcionarios. */
	private UsuariosFuncionario funcionarioEntity;
	private Persona personaEntity;

	/** Variables para control de componentes en pantalla. */
	private String usuario;
	private long municipioId;
	private boolean existePersona;
	private boolean mostrarTabla;
	private boolean esRegistro;
	private boolean ejecutoConsulta;

	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		this.funcionarioEntity = new UsuariosFuncionario();
		//Se obtiene usuario de sesión
		usuarioSesion = (UsuarioSesion)this.obtenerVariableSesion(Constantes.USUARIO_SESION);
		
		this.municipioId = 175;
		try {
			this.lstFuncionarios = iParametrizacionFacade.obtenerFuncionarios(this.municipioId,
					Constantes.TODOS);
			this.mostrarTabla = this.lstFuncionarios.size() > 0 ? true : false;
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());			
		}
	}

	/**
	 * COnsulta la lista de funcionarios parametrizados en la alcaldï¿½a.
	 */
	public void buscar(){
		this.ejecutoConsulta = true;
		this.lstFuncionarios.clear();
		try {
			if (this.usuario != null && !this.usuario.isEmpty()) {
				this.funcionarioEntity = iParametrizacionFacade.obtenerFuncionario(usuario);
				if (this.funcionarioEntity != null) {
					this.lstFuncionarios.add(this.funcionarioEntity);
				}
			} else {
				this.lstFuncionarios = iParametrizacionFacade.obtenerFuncionarios(municipioId,
						Constantes.TODOS);
			}
			this.mostrarTabla = this.lstFuncionarios.size() > 0 ? true : false; 
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Prepara la forma para la creaciï¿½n o modificaciï¿½n de un funcionario.
	 * @param esModificaion
	 */
	public String prepararForma(){
		this.esRegistro = true;
		this.funcionarioEntity = new UsuariosFuncionario();
		this.funcionarioEntity.setEstado(Constantes.ACTIVO);
//		this.funcionarioEntity.setRolId(Constantes.ROL_FUNCIONARIO);
		this.funcionarioEntity.setMunicipioId(this.municipioId);
		this.personaEntity = new Persona();
        	
		return "";
	}

	/**
	 * Verifica a travï¿½s del nï¿½mero de identificaciï¿½n ingresado
	 * si la persona ya fue creada en la base de datos. 
	 */
	public void buscarPersona(){
		Persona personaAux = null;
		try {
			personaAux = iParametrizacionFacade.obtenerPersona(this.personaEntity.getNumIdentificacion());
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}

		//Si ya existe la persona se mapea al objeto en pantalla.
		if (personaAux != null) {
			this.existePersona = true;
			this.personaEntity = personaAux;
//			this.personaEntity.setNombres(personaAux.getNombres());
//			this.personaEntity.setApellidos(personaAux.getApellidos());
//			this.personaEntity.setDireccion(personaAux.getDireccion());
//			this.personaEntity.setEmail(personaAux.getEmail());
//			this.personaEntity.setTelefono(personaAux.getTelefono());
//			this.personaEntity.setTipoIdentificacion(personaAux.getTipoIdentificacion());
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

		//Se invoca el mï¿½todo de registro
		try {
			if (this.existePersona) {
				iParametrizacionFacade.actualizarPersona(this.personaEntity);
			} 
			iParametrizacionFacade.registrarFuncionario(this.funcionarioEntity);
			buscar();
			this.adicionarMensaje(Constantes.INFO.charAt(0), "Se registrï¿½ el funcionario exitosamente.");
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}

	}
	
	/**
	 * Ejecuta el mï¿½todo encargado de la actualizaciï¿½nd e los datos del funcionario.
	 */
	public void actualizar(){
		try {
			iParametrizacionFacade.actualizarPersona(this.personaEntity);
			iParametrizacionFacade.actualizarFuncionario(this.funcionarioEntity);
			this.adicionarMensaje(Constantes.INFO.charAt(0), "Se actualizï¿½ la informaciï¿½n de manera exitosa.");
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	/**
	 * Inhabilita o Activa un funcionario en la alcaldía específica. 
	 */
	public void cambiarEstado(String estado){
		this.funcionarioEntity.setEstado(estado);
		try {
			iParametrizacionFacade.actualizarFuncionario(this.funcionarioEntity);
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
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
	 * Setea el objeto funcionario con le ingresado por parámetro.
	 * 
	 * @param funcionario
	 */
	public void setObject(UsuariosFuncionario funcionario){
		this.esRegistro = false;
		this.personaEntity = funcionario.getPersona();
		this.funcionarioEntity = funcionario;
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

	/**
	 * @return the mostrarTabla
	 */
	public boolean isMostrarTabla() {
		return mostrarTabla;
	}

	/**
	 * @param mostrarTabla the mostrarTabla to set
	 */
	public void setMostrarTabla(boolean mostrarTabla) {
		this.mostrarTabla = mostrarTabla;
	}

	/**
	 * @return the esRegistro
	 */
	public boolean isEsRegistro() {
		return esRegistro;
	}

	/**
	 * @param esRegistro the esRegistro to set
	 */
	public void setEsRegistro(boolean esRegistro) {
		this.esRegistro = esRegistro;
	}

	/**
	 * @return the ejecutoConsulta
	 */
	public boolean isEjecutoConsulta() {
		return ejecutoConsulta;
	}

	/**
	 * @param ejecutoConsulta the ejecutoConsulta to set
	 */
	public void setEjecutoConsulta(boolean ejecutoConsulta) {
		this.ejecutoConsulta = ejecutoConsulta;
	}
	
}
