package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
 * Managed bean para manejo de campos de pantalla de creación de formualrios dinámicos. 
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
	private List<DominioVO> lstTiposEntrada;
	private List<TipoCampo> lstTiposCampos;
	private List<DominioVO> lstEstados;
	
	/** Entidades para Formulario. */
	private Formulario formulario;
	private CampoFormulario campoFormulario;
	
	
	/**
	 *  Incializador de variables del bean
	 */
	@PostConstruct
	public void init() {
		this.lstTiposEntrada = new ArrayList<>();
		this.lstTiposCampos = new ArrayList<>();
		this.lstEstados = new ArrayList<>();
		this.formulario = new Formulario();
		this.formulario.setCamposFormularios(new ArrayList<CampoFormulario>());
		
		try {
			this.lstTiposCampos = iParamTramitesFacade.obtenerTiposCampoForm();
			
			//Lista de tipos de entrada.
			this.lstTiposEntrada.add(new DominioVO(Constantes.TIPO_ENTRADA_NUMERO, Constantes.TIPO_ENTRADA_NUMERO));
			this.lstTiposEntrada.add(new DominioVO(Constantes.TIPO_ENTRADA_CARACTER, Constantes.TIPO_ENTRADA_CARACTER));
			this.lstTiposEntrada.add(new DominioVO(Constantes.TIPO_ENTRADA_EMAIL, Constantes.TIPO_ENTRADA_EMAIL));
			
			//Lista de estados
			this.lstEstados.add(new DominioVO(Constantes.ACTIVO, Constantes.LBL_ESTADO_ACTIVO));
			this.lstEstados.add(new DominioVO(Constantes.INACTIVO, Constantes.LBL_ESTADO_INACTIVO));
			
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());		
		}
	}
	
	/**
	 * Inicializa un nuevo campo para el formulario. 
	 */
	public void inicializarNuevoCampo(){
		this.campoFormulario = new CampoFormulario();
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
	 * Persiste en BD el formulario creado.
	 * @return
	 */
	public String guardarFormulario(){
		try {
			iParamTramitesFacade.crearFormulario(this.formulario);
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
		}
		
		//La redirección aún no se ha definido. 
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
	 * @return the lstEstados
	 */
	public List<DominioVO> getLstEstados() {
		return lstEstados;
	}

	/**
	 * @param lstEstados the lstEstados to set
	 */
	public void setLstEstados(List<DominioVO> lstEstados) {
		this.lstEstados = lstEstados;
	}
	
}
