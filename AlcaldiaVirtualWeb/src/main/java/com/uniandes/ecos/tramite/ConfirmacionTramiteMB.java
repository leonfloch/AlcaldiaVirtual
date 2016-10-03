/**
 * 
 */
package com.uniandes.ecos.tramite;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.util.Constantes;

/**
 * Bean encargado de mostrar el numero de tramite generado al
 * ciudadano
 * @author 80221940
 *
 */
@ViewScoped
@ManagedBean
public class ConfirmacionTramiteMB  extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Numero de tramite generado
	 */
	private long numeroTramite;
	
	/**
	 * Postconstructor del bean
	 */
	@PostConstruct
	public void init() {
		numeroTramite = (long)this.obtenerVariableSesion(Constantes.SESION_TRAMITE_ID);
	}
	
	/**
	 * Redirecciona a la pantalla de consulta de tipos de tramites
	 * @return
	 */
	public String aceptar() {
		return RutasApp.CONSULTA_TIPO_TRAMITE;
	}

	/**
	 * @return the numeroTramite
	 */
	public long getNumeroTramite() {
		return numeroTramite;
	}

	/**
	 * @param numeroTramite the numeroTramite to set
	 */
	public void setNumeroTramite(long numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

}
