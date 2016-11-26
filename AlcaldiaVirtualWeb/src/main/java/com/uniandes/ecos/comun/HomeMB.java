/**
 * 
 */
package com.uniandes.ecos.comun;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.facade.ProcesadorTramitesFacade;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * pantalla de home solo para usuarios ciudadanos
 * @author leonardovalbuenacalderon
 *
 */
@ViewScoped
@ManagedBean
public class HomeMB extends BaseMBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IProcesadorTramitesFacade procesadorTramitesFacade;
	
	/**
	 * tramites del ciudadano en session
	 */
	private List<Tramite> tramitesCiudadano;
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		this.obtenerTramites();
	}
	
	/**
	 * indica si el usuario logeado es ciudadano
	 * @return
	 */
	public boolean isCiudadano() {
		boolean ciudadano = false;
		if (Constantes.ROL_CIUDADANO == this.getSesion().getRole().getRolId()) {
			ciudadano = true;
		}
		return ciudadano;
	}
	
	/**
	 * Carga los tramites del ciudadano logeado
	 */
	private void obtenerTramites() {
		if (this.isCiudadano()) {
			try {
				tramitesCiudadano = procesadorTramitesFacade.obtenerTramitesCiudadano(
						this.getSesion().getUsuario(), Constantes.MUNICIPIO_ID_DEMO);
			} catch (NegocioException e) {
				tramitesCiudadano = new ArrayList<Tramite>();
			}
		}
	}

	/**
	 * @return the tramitesCiudadano
	 */
	public List<Tramite> getTramitesCiudadano() {
		return tramitesCiudadano;
	}

	/**
	 * @param tramitesCiudadano the tramitesCiudadano to set
	 */
	public void setTramitesCiudadano(List<Tramite> tramitesCiudadano) {
		this.tramitesCiudadano = tramitesCiudadano;
	}

}
