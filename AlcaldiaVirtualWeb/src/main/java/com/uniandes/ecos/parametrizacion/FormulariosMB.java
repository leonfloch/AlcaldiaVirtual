package com.uniandes.ecos.parametrizacion;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Managed bean para manejo de campos de pantalla en la administración de formularios. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 13/08/2016
 */
@ViewScoped
@ManagedBean
public class FormulariosMB extends BaseMBean {
	
	/** Serial de la clase. */
	private static final long serialVersionUID = 1L;
	
	/** Inyección de dependencia con fachadas de parametrización.  */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;
	
	/** Listas en pantalla. */
	private List<Formulario> lstFormularios;
	
	/** Entidades en pantalla. */
	private Formulario formularioSeleccionado;
	
	/** Variables para control de pantalla. */
	private String nombreFormulario;
	private boolean ejecutoConsulta;
	
	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		try {
			this.lstFormularios = iParamTramitesFacade.obtenerFormularios(null);
			
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());		
		}
	}
	
	/**
	 * Consulta la lista de formularios de acuerdo al nombre ingresado en el filtro. 
	 */
	public void buscarFormularios(){
		try {
			this.lstFormularios = iParamTramitesFacade.obtenerFormularios(this.nombreFormulario);
			this.ejecutoConsulta = true;
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Redirecciona a la forma para editar o crear el formulario. 
	 */
	public String redireccionarFormulario(boolean esEdicion){
		if (esEdicion){
			this.adicionarVariableSesion(Constantes.FORMULARIO_MODIFICAR_ID, this.formularioSeleccionado.getFormularioId());
		}
		return RutasApp.FORMULARIOS_CREACION_RUTA;
	}
	
	/**
	 * @return the lstFormularios
	 */
	public List<Formulario> getLstFormularios() {
		return lstFormularios;
	}

	/**
	 * @param lstFormularios the lstFormularios to set
	 */
	public void setLstFormularios(List<Formulario> lstFormularios) {
		this.lstFormularios = lstFormularios;
	}

	/**
	 * @return the nombreFormulario
	 */
	public String getNombreFormulario() {
		return nombreFormulario;
	}

	/**
	 * @param nombreFormulario the nombreFormulario to set
	 */
	public void setNombreFormulario(String nombreFormulario) {
		this.nombreFormulario = nombreFormulario;
	}

	/**
	 * @return the formularioSeleccionado
	 */
	public Formulario getFormularioSeleccionado() {
		return formularioSeleccionado;
	}

	/**
	 * @param formularioSeleccionado the formularioSeleccionado to set
	 */
	public void setFormularioSeleccionado(Formulario formularioSeleccionado) {
		this.formularioSeleccionado = formularioSeleccionado;
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
