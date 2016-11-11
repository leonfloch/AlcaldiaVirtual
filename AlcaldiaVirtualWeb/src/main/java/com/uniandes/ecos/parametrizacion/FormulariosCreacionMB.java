package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.CampoFormulario;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.DominioVO;
import com.uniandes.ecos.util.NegocioException;

/**
 * Managed bean para manejo de campos de pantalla de creación de formualrios dinámicos. 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 13/08/2016
 */
@ViewScoped
@ManagedBean
public class FormulariosCreacionMB extends BaseMBean {
	
	/** Serial de la clase. */
	private static final long serialVersionUID = 1L;
	
	/** Inyección de dependencia con fachadas de parametrización.  */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;
	
	/** Inyección con MB de Formulario dinámico. */
	private FormularioDinamicoMB formularioDinamicoMB;
	
	/** Listas en pantalla. */
	private List<DominioVO> lstTiposEntrada;
	private List<TipoCampo> lstTiposCampos;
	
	/** Entidades para Formulario. */
	private Formulario formulario;
	private CampoFormulario campoFormulario;
	
	/** Variables para control de pantalla. */
	private boolean modificacion;
	private boolean inputText;
	private boolean modificacionformulario;
	
	/** PanelGrid del formulario dinámico */
	private PanelGrid panelGrid;
	
	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		this.lstTiposEntrada = new ArrayList<>();
		this.campoFormulario = new CampoFormulario();
		this.inputText = true;
		this.formulario = new Formulario();
		this.formulario.setCamposFormularios(new ArrayList<CampoFormulario>());
		
		try {
			//Se obtiene variable de sesión en caso de ser una modificación. 
			if (this.obtenerVariableSesion(Constantes.FORMULARIO_MODIFICAR_ID) != null ) {
				long formularioId = (long) this.obtenerVariableSesion(Constantes.FORMULARIO_MODIFICAR_ID);
				this.formulario = iParamTramitesFacade.obtenerFormulario(formularioId);
				this.modificacionformulario = true;
				this.removerVariableSesion(Constantes.FORMULARIO_MODIFICAR_ID);
			}
			
			this.lstTiposCampos = iParamTramitesFacade.obtenerTiposCampoForm();
			
			//Lista de tipos de entrada.
			this.lstTiposEntrada.add(new DominioVO(Constantes.TIPO_ENTRADA_NUMERO, Constantes.TIPO_ENTRADA_NUMERO_VALUE));
			this.lstTiposEntrada.add(new DominioVO(Constantes.TIPO_ENTRADA_CARACTER, Constantes.TIPO_ENTRADA_CARACTER));
			this.lstTiposEntrada.add(new DominioVO(Constantes.TIPO_ENTRADA_EMAIL, Constantes.TIPO_ENTRADA_EMAIL));
			
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());		
		}
	}
	
	
	/**
	 * Inicializa un nuevo campo para el formulario. 
	 */
	public void inicializarCampo(boolean esCreacion){
		if (esCreacion) {
			this.modificacion = false;
			this.inputText = true;
			this.campoFormulario = new CampoFormulario();
		}else{
			this.modificacion = true;
		}
	}
	
	/**
	 * Adiciona un nuevo campo al formulario. 
	 */
	public void adicionarCampo(){
		String mensaje = validarCampo();
		if (mensaje.isEmpty()) {
			this.formulario.addCamposFormulario(this.campoFormulario);
		}else{
			this.adicionarMensaje(Constantes.ERROR, mensaje);
			return;
		}
		
		//Si no hay errores se cierra el popup.
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dlgCampoNuevo').hide();");
	}
	
	/**
	 * Vala las condiciones de actualización del campo. 
	 */
	public void actualizarCampo(){
		String mensaje = validarCampo();
		if (!mensaje.isEmpty()) {
			this.adicionarMensaje(Constantes.ERROR, mensaje);
			return;
		}
		
		//Si no hay errores se cierra el popup.
		RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgCampoNuevo').hide();");
	}
	
	/**
	 * Elimmina el campo seleccionado del formulario.
	 * 
	 * @param campoAEliminar
	 */
	public void eliminarCampo(CampoFormulario campoAEliminar){
		this.formulario.getCamposFormularios().remove(campoAEliminar);
	}
	
	/**
	 * Escucha del evento de cambio de tipo campo. 
	 * @param event
	 */
	public void cambiarTipoCampo(ValueChangeEvent event){
		TipoCampo tipoCampo = (TipoCampo) event.getNewValue();
		if (Constantes.TIPO_CAMPO_INPUT.equals(tipoCampo.getNombre())) {
			this.inputText = true;
		}else{
			this.inputText = false;
		}
	}
	
	/**
	 * Persiste en BD el formulario creado.
	 * @return
	 */
	public String guardarFormulario(){
		if (this.formulario.getNombre() == null || this.formulario.getNombre().trim().isEmpty()) {
			this.adicionarMensaje(Constantes.ERROR, "El campo nombre no puede estar vac\u00EDo.");
			return "";
		} else if(this.formulario.getCamposFormularios().size() == 0){
			this.adicionarMensaje(Constantes.ERROR, "El formulario debe tener al menos un campo asociado.");
			return "";
		}
		try {
			iParamTramitesFacade.crearFormulario(this.formulario);
			return RutasApp.FORMULARIOS_ADMINISTRACION_RUTA;
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
			return "";
		}
		
	}
	
	/**
	 * Actualiza en BD el formulario.
	 * @return
	 */
	public String actualizarFormulario(){
		if (this.formulario.getNombre() == null || this.formulario.getNombre().trim().isEmpty()) {
			this.adicionarMensaje(Constantes.ERROR, "El campo nombre no puede estar vac\u00EDo.");
			return "";
		} else if(this.formulario.getCamposFormularios().size() == 0){
			this.adicionarMensaje(Constantes.ERROR, "El formulario debe tener al menos un campo asociado.");
			return "";
		}
		try {
			iParamTramitesFacade.actualizarFormulario(this.formulario);
			return RutasApp.FORMULARIOS_ADMINISTRACION_RUTA;
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
			return "";
		}
		
	}
	
	/**
	 * Valida que no se ingresen campos con nombres repetidos o con posiciones que ya existen. 
	 * @return un string vacío si el campo es válido, si no, retorna el mensaje de error. 
	 */
	private String validarCampo(){
		String mensaje = "";
		
		
		for (CampoFormulario campoAux : this.formulario.getCamposFormularios()) {
			if (campoAux.getCampoId() != this.campoFormulario.getCampoId() && campoAux.getNombre().trim().toUpperCase().equals(this.campoFormulario.getNombre().trim().toUpperCase())) {
				mensaje = "Ya existe un campo en el formulario con el label ingresado";
				break;
			} 
//			else if(campoAux.getPosicion() == this.campoFormulario.getPosicion()){
//				mensaje = "Ya existe un campo para el formulario con la posici\u00F3n ingresada."; 
//				break;
//			}
		}
		
		return mensaje;
	}
	
	/**
	 * Previsualiza el formulario de acuerdo a los campos agregados. 
	 */
	public String armarFormulario(){
//		FacesContext fc = FacesContext.getCurrentInstance();
//		Application application = fc.getApplication();
//
//		panelGrid = (PanelGrid) application.createComponent(fc, "org.primefaces.component.PanelGrid", "org.prime.component.PanelGridRenderer");
//		panelGrid.setId("panelGridFormulario");
//		panelGrid.setColumns(2);
//		
//		HtmlOutputText text = new HtmlOutputText();
//		text.setId("t");
//		text.setValue("Esta es una prueba");
//		
//		HtmlOutputText text1 = new HtmlOutputText();
//		text1.setId("t1");
//		text1.setValue("Esta es una prueba");
//		
//		HtmlOutputText text2 = new HtmlOutputText();
//		text2.setId("t2");
//		text2.setValue("Esta es una prueba");
//		
//		panelGrid.getChildren().add(text);
//		panelGrid.getChildren().add(text1);
//		panelGrid.getChildren().add(text2);
		this.adicionarVariableSesion("formulario", this.formulario);
		return "formularioDinamico.jsf?faces-redirect=true";
	}
	
	/**
	 * Redirecciona a la página de aministración de formularios.
	 * @return
	 */
	public String volver(){
		return RutasApp.FORMULARIOS_ADMINISTRACION_RUTA;
	}

	/** 
	 * Setea el campo formulario a modificar. 
	 * @param index
	 */
	public void setObjectCampo(int index){
		this.campoFormulario = this.formulario.getCamposFormularios().get(index);
	}
	
	/**
	 * @return the lstTiposEntrada
	 */
	public List<DominioVO> getLstTiposEntrada() {
		return lstTiposEntrada;
	}

	/**
	 * @param lstTiposEntrada the lstTiposEntrada to set
	 */
	public void setLstTiposEntrada(List<DominioVO> lstTiposEntrada) {
		this.lstTiposEntrada = lstTiposEntrada;
	}

	/**
	 * @return the lstTiposCampos
	 */
	public List<TipoCampo> getLstTiposCampos() {
		return lstTiposCampos;
	}

	/**
	 * @param lstTiposCampos the lstTiposCampos to set
	 */
	public void setLstTiposCampos(List<TipoCampo> lstTiposCampos) {
		this.lstTiposCampos = lstTiposCampos;
	}

	/**
	 * @return the formulario
	 */
	public Formulario getFormulario() {
		return formulario;
	}

	/**
	 * @param formulario the formulario to set
	 */
	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	/**
	 * @return the campoFormulario
	 */
	public CampoFormulario getCampoFormulario() {
		return campoFormulario;
	}

	/**
	 * @param campoFormulario the campoFormulario to set
	 */
	public void setCampoFormulario(CampoFormulario campoFormulario) {
		this.campoFormulario = campoFormulario;
	}

	/**
	 * @return the inputText
	 */
	public boolean isInputText() {
		return inputText;
	}

	/**
	 * @param inputText the inputText to set
	 */
	public void setInputText(boolean inputText) {
		this.inputText = inputText;
	}

	/**
	 * @return the modificacion
	 */
	public boolean isModificacion() {
		return modificacion;
	}

	/**
	 * @param modificacion the modificacion to set
	 */
	public void setModificacion(boolean modificacion) {
		this.modificacion = modificacion;
	}


	/**
	 * @return the modificacionformulario
	 */
	public boolean isModificacionformulario() {
		return modificacionformulario;
	}


	/**
	 * @param modificacionformulario the modificacionformulario to set
	 */
	public void setModificacionformulario(boolean modificacionformulario) {
		this.modificacionformulario = modificacionformulario;
	}


	/**
	 * @return the panelGrid
	 */
	public PanelGrid getPanelGrid() {
		return panelGrid;
	}


	/**
	 * @param panelGrid the panelGrid to set
	 */
	public void setPanelGrid(PanelGrid panelGrid) {
		this.panelGrid = panelGrid;
	}

}
