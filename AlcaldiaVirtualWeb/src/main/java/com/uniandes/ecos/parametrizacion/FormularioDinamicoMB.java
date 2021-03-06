package com.uniandes.ecos.parametrizacion;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;

import org.primefaces.component.panelgrid.PanelGrid;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.CampoFormulario;
import com.uniandes.ecos.entities.CampoFormularioTramite;
import com.uniandes.ecos.entities.DocumentoRequerido;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.FormularioTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.util.Constantes;

/**
 * Managed bean para manejo de previsualizaci�n de formularios din�micos. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 08/11/2016
 */
@ManagedBean
public class FormularioDinamicoMB extends BaseMBean {
	
	/** Serial de la clase */
	private static final long serialVersionUID = 1L;

	/** PanelGrid del formulario din�mico */
	private PanelGrid panelGrid;
	
	/** Formulario din�mico. */
	private Formulario formulario;
	
	/** Tr�mite del formulario. */
	private Tramite tramite;
	
	/** Documento Requerido */
	private DocumentoRequerido docRequerido;
	
	/** Instancei del contexto de la aplicaci�n. */
	private Application application;
	
	/** Ruta de invocaci�n del formulario din�mico */
	private String rutaInvocacion;
	
	/** Indica si el formulario se est� cargando desde un tr�mite. */
	private boolean enTramite;
	
	/** indica si s�lo se visualizan valores. */
	private boolean soloVisualiza;
	
	/**
	 * Inicializaci�n de variables de clase.
	 */
	@PostConstruct
	public void init(){
		this.formulario = (Formulario) this.obtenerVariableSesion(Constantes.FORMULARIO_DINAMICO);
		this.rutaInvocacion = (String) this.obtenerVariableSesion(Constantes.RUTA_INVOCACION_FORMULARIO);
		this.tramite = (Tramite) this.obtenerVariableSesion(Constantes.SESION_TRAMITE);
		this.docRequerido = (DocumentoRequerido) this.obtenerVariableSesion(Constantes.SESION_DOC_REQ);
		this.enTramite = this.tramite != null ? true : false;
		if (this.enTramite && this.tramite != null && this.tramite.getFormulariosTramites().size() > 0) {
			this.soloVisualiza = true;
		}

		FacesContext fc = FacesContext.getCurrentInstance();
		application = fc.getApplication();

		panelGrid = (PanelGrid) application.createComponent(fc, "org.primefaces.component.PanelGrid", "org.prime.component.PanelGridRenderer");
		panelGrid.setId("panelGridFormulario");
		panelGrid.setColumns(4);
		panelGrid.setStyle("border-style: none !important");

		if (this.soloVisualiza) {
			for (FormularioTramite formularioAprocesar : this.tramite.getFormulariosTramites()) {
				if (formularioAprocesar.getNombre().equals(this.formulario.getNombre())) {
					for (CampoFormularioTramite campo : formularioAprocesar.getCamposFormularioTramites()) {
						HtmlOutputText outputTextNombre = new HtmlOutputText();
						HtmlInputText inputTextValor = new HtmlInputText();
						
						outputTextNombre.setValue(campo.getNombre());
						inputTextValor.setValue(campo.getValor());
						inputTextValor.setDisabled(true);
						panelGrid.getChildren().add(outputTextNombre);
						panelGrid.getChildren().add(inputTextValor);
					}
				}
			}
		}else{
			int cont = 0;
			for (CampoFormulario campo : formulario.getCamposFormularios()) {
				UIComponent campoForm = null;
				switch (campo.getTiposCampo().getNombre()) {
				case Constantes.INPUTTEXT:
					campoForm = armarTextField(campo, cont);
					break;

				case Constantes.INPUTTEXTAREA:
					campoForm = armarTextArea(campo, cont);
					break;

				case Constantes.CHECKBOX:
					campoForm = armarCheckBox(campo, cont);
					break;

				case Constantes.ONERADIO:
					campoForm = armarOneRadio(campo, cont);
					break;

				case Constantes.OUTPUTTEXT:
					campoForm = armarOutputext(campo, cont);
					break;

				default:
					break;
				}

				HtmlOutputText outputText = new HtmlOutputText();
				outputText.setValue(campo.getNombre());
				panelGrid.getChildren().add(outputText);
				panelGrid.getChildren().add(campoForm);
				cont++;
			} 
		}

	}
	
	/**
	 * Arma un componante html de tipo text field. 
	 * 
	 * @param campo
	 * @param id
	 * @return
	 */
	private UIComponent armarTextField(CampoFormulario campo, int id){
		HtmlInputText inputText = new HtmlInputText();
		
		inputText.setId("txtF"+id);
		inputText.setMaxlength(campo.getLongitud().intValue());
		inputText.setLabel(campo.getNombre());
		inputText.setRequired(Constantes.SI.equals(campo.getRequerido()) ? true : false);
		inputText.setRequiredMessage(campo.getTextoError());
		inputText.setOnmouseover(campo.getTextoAyuda());
		
		return inputText;
	}
	
	/**
	 * Arma un componante html de tipo text area.
	 * 
	 * @param campo
	 * @param id
	 * @return
	 */
	private UIComponent armarTextArea(CampoFormulario campo, int id){
		HtmlInputTextarea inputTextArea = new HtmlInputTextarea();
		
		inputTextArea.setId("txtA"+id);
		inputTextArea.setLabel(campo.getNombre());
		inputTextArea.setRequired(Constantes.SI.equals(campo.getRequerido()) ? true : false);
		inputTextArea.setRequiredMessage(campo.getTextoError());
		inputTextArea.setOnmouseover(campo.getTextoAyuda());
		
		return inputTextArea;
	}
		
	/**
	 * Arma un componante html de tipo one radio.
	 * 
	 * @param campo
	 * @param id
	 * @return
	 */
	private UIComponent armarOneRadio(CampoFormulario campo, int id){
		HtmlSelectOneRadio OneRadio = new HtmlSelectOneRadio();
		
		OneRadio.setId("oneR"+id);
		OneRadio.setLabel(campo.getNombre());
		OneRadio.setRequired(Constantes.SI.equals(campo.getRequerido()) ? true : false);
		OneRadio.setRequiredMessage(campo.getTextoError());
		OneRadio.setOnmouseover(campo.getTextoAyuda());

		return OneRadio;
	}
	
	/**
	 * Arma un componante html de tipo text area.
	 * 
	 * @param campo
	 * @param id
	 * @return
	 */
	private UIComponent armarCheckBox(CampoFormulario campo, int id){
		HtmlSelectBooleanCheckbox checkBox = new HtmlSelectBooleanCheckbox();
		
		checkBox.setId("chk"+id);
		checkBox.setLabel(campo.getNombre());
		checkBox.setRequired(Constantes.SI.equals(campo.getRequerido()) ? true : false);
		checkBox.setRequiredMessage(campo.getTextoError());
		checkBox.setOnmouseover(campo.getTextoAyuda());

		return checkBox;
	}
	
	/**
	 * Arma un componante html de tipo text area.
	 * 
	 * @param campo
	 * @param id
	 * @return
	 */
	private UIComponent armarOutputext(CampoFormulario campo, int id){
		HtmlOutputText outputext = new HtmlOutputText();
		
		outputext.setId("chk"+id);
		outputext.setValue(campo.getNombre());

		return outputext;
	}
	
	/**
	 * Obtiene los valores digitados formulario din�mico.
	 */
	public FormularioTramite obtenerValores(){
		FormularioTramite formularioTramite = new FormularioTramite();
		formularioTramite.setNombre(this.formulario.getNombre());
		formularioTramite.setTramite(this.tramite);
		
		for (CampoFormulario campo : this.formulario.getCamposFormularios()) {
			CampoFormularioTramite campoFormularioTramite = new CampoFormularioTramite();
			campoFormularioTramite.setNombre(campo.getNombre());
			campoFormularioTramite.setFormulariosTramite(formularioTramite);
			UIInput campoDigitado = null;
			
			switch (campo.getTiposCampo().getNombre()) {
			case Constantes.INPUTTEXT:
				campoDigitado = (UIInput) this.panelGrid.findComponent("txtF"+campo.getPosicion());
				campoFormularioTramite.setValor((String) campoDigitado.getValue());
				break;

			case Constantes.INPUTTEXTAREA:
				campoDigitado = (UIInput) this.panelGrid.findComponent("txtA"+campo.getPosicion());
				campoFormularioTramite.setValor((String) campoDigitado.getValue());
				break;

			case Constantes.CHECKBOX:
				campoDigitado = (UIInput) this.panelGrid.findComponent("chk"+campo.getPosicion());
				String campoValor = (boolean) campoDigitado.getValue() ? Constantes.SI : Constantes.NO;
				campoFormularioTramite.setValor(campoValor);
				break;

			case Constantes.ONERADIO:
				campoDigitado = (UIInput) this.panelGrid.findComponent("oneR"+campo.getPosicion());
				campoFormularioTramite.setValor((String) campoDigitado.getValue());
				break;

			case Constantes.OUTPUTTEXT:
				campoDigitado = (UIInput) this.panelGrid.findComponent("chk"+campo.getPosicion());
				campoFormularioTramite.setValor((String) campoDigitado.getValue());
				break;

			default:
				break;
			}
			
			formularioTramite.getCamposFormularioTramites().add(campoFormularioTramite);
		}
		
		return formularioTramite;
	}
	
	/**
	 * Guarda los valores introducidos en el 
	 * @return
	 */
	public String guardar(){
		this.tramite.getFormulariosTramites().add(obtenerValores());
		this.docRequerido.setEstadoUpload(true);
		return this.rutaInvocacion;
	}

	/** 
	 * Redirecciona a la p�gina en la cual fue inyectado el managed bean. 
	 * 
	 * @return
	 */
	public String volver(){
		this.adicionarVariableSesion(Constantes.FORMULARIO_DINAMICO, this.formulario);
		return this.rutaInvocacion;
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
	 * @return the rutaInvocacion
	 */
	public String getRutaInvocacion() {
		return rutaInvocacion;
	}

	/**
	 * @param rutaInvocacion the rutaInvocacion to set
	 */
	public void setRutaInvocacion(String rutaInvocacion) {
		this.rutaInvocacion = rutaInvocacion;
	}

	/**
	 * @return the enTramite
	 */
	public boolean isEnTramite() {
		return enTramite;
	}

	/**
	 * @param enTramite the enTramite to set
	 */
	public void setEnTramite(boolean enTramite) {
		this.enTramite = enTramite;
	}

	/**
	 * @return the soloVisualiza
	 */
	public boolean isSoloVisualiza() {
		return soloVisualiza;
	}

	/**
	 * @param soloVisualiza the soloVisualiza to set
	 */
	public void setSoloVisualiza(boolean soloVisualiza) {
		this.soloVisualiza = soloVisualiza;
	}
	
}
