package com.uniandes.ecos.parametrizacion;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.util.NegocioException;

@ViewScoped
@ManagedBean
public class TipoTramiteMB extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698695809011793289L;

	/**
	 * Servicio de parametrizacion
	 */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;

	/**
	 * Lista de tipos tramite
	 */
	private List<TipoTramite> tiposTramite;

	/**
	 * Tipo de tramite seleccionado para edicion
	 */
	private TipoTramite tipoTramiteSelecc;

	/**
	 * indica si se cra un nuevo funcionario
	 */
	private boolean creacion;

	/**
	 * Constructor
	 */
	public TipoTramiteMB() {
		creacion = false;
		this.initTiposTramite();
	}
	
	@PostConstruct
	public void init(){
		this.consultarTiposTramite();
	}

	/**
	 * Se ejecuta antes de abril el modal de edicion de tipos tramite
	 */
	public void preModal() {
		if (creacion) {
			this.initTiposTramite();
		}
	}
	
	public void consultarTiposTramite(){
		try {
			tiposTramite = iParamTramitesFacade.obtenerListaTramites(tipoTramiteSelecc.getNombre());
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	public void persistirTipoTramite(){
		try {
			iParamTramitesFacade.crearTipoTramite(tipoTramiteSelecc);
			consultarTiposTramite();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Limpia la informacion del tipo tramite
	 */
	private void initTiposTramite() {
		tipoTramiteSelecc = new TipoTramite();

	}

	/**
	 * @return the tiposTramite
	 */
	public List<TipoTramite> getTiposTramite() {
		return tiposTramite;
	}

	/**
	 * @param tiposTramite
	 *            the tiposTramite to set
	 */
	public void setTiposTramite(List<TipoTramite> tiposTramite) {
		this.tiposTramite = tiposTramite;
	}

	/**
	 * @return the tipoTramiteSelecc
	 */
	public TipoTramite getTipoTramiteSelecc() {
		return tipoTramiteSelecc;
	}

	/**
	 * @param tipoTramiteSelecc
	 *            the tipoTramiteSelecc to set
	 */
	public void setTipoTramiteSelecc(TipoTramite tipoTramiteSelecc) {
		this.tipoTramiteSelecc = tipoTramiteSelecc;
	}

	/**
	 * @return the creacion
	 */
	public boolean isCreacion() {
		return creacion;
	}

	/**
	 * @param creacion
	 *            the creacion to set
	 */
	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
	}

}
