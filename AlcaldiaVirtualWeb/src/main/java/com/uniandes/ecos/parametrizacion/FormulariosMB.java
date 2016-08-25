package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.CampoFormulario;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.DominioVO;
import com.uniandes.ecos.util.NegocioException;

/**
 * Managed bean para manejo de campos de pantalla de creaci�n de formualrios din�micos. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 13/08/2016
 */
@ViewScoped
@ManagedBean
public class FormulariosMB extends BaseMBean {
	
	/** Serial de la clase. */
	private static final long serialVersionUID = 1L;
	
	/** Inyecci�n de dependencia con fachadas de parametrizaci�n.  */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;
	
	/** Listas en pantalla. */
	private List<DominioVO> lstTiposEntrada;
	private List<TipoCampo> lstTiposCampos;
	
	/** Entidades para Formulario. */
	private Formulario formulario;
	private CampoFormulario campoFormulario;
	
	/** Variables para control de pantalla. */
	private boolean modificacion;
	private boolean inputText;
	
	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		this.lstTiposEntrada = new ArrayList<>();
		this.lstTiposCampos = new ArrayList<>();
		this.formulario = new Formulario();
		this.formulario.setCamposFormularios(new ArrayList<CampoFormulario>());
		this.campoFormulario = new CampoFormulario();
		this.inputText = true;
		
		try {
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
		this.formulario.addCamposFormulario(this.campoFormulario);
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
		}
		try {
			iParamTramitesFacade.crearFormulario(this.formulario);
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
		}
		
		//La redirecci�n a�n no se ha definido. 
		return "";
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
	
}
