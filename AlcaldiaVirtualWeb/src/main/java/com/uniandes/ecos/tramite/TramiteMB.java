/**
 * 
 */
package com.uniandes.ecos.tramite;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FlowEvent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.util.Constantes;

/**
 * mbean encargado de realizar el proceso de tramites para los 
 * ciudadanos
 * @author Leonardo Valbuena 
 *
 */
@SessionScoped
@ManagedBean
public class TramiteMB extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista de tipos de tramites que puede realizar el ciudadano
	 */
	private List<TipoTramite> tiposTramites;
	
	/**
	 * Municipio de la alcaldia donde se redirecciono al ciudadano
	 */
	private Municipio alcaldiaMunicipio;
	
	/**
	 * indica si se salta el wizard
	 */
	private boolean skip;
	
	/**
	 * tipo tramite seleccionado por el ciudadano
	 */
	private TipoTramite tipoTramite;
	
	

	/**
	 * Constructor
	 */
	public TramiteMB() {
		skip = false;
		tiposTramites = new ArrayList<TipoTramite>();
	}
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		alcaldiaMunicipio = (Municipio)obtenerVariableSesion(Constantes.SESION_MUNICIPIO_CIUDADANO);
		this.cargarTiposTramites();
		
	}
	
	/**
	 * Inicia un tramite con el tipo de tramite
	 * seleccionado
	 * @return
	 */
	public String iniciarTramite() {		
		return RutasApp.CREAR_TRAMITE;
	}
	
	/**
	 * Se cargan los tipos de tramites para el municipio en sesion
	 */
	private void cargarTiposTramites() {

		for (TramiteXMunicipio tramiteXMunicipio : alcaldiaMunicipio.getTramitesXMunicipios()) {
			tiposTramites.add(tramiteXMunicipio.getTiposTramite());
		}
	}
	
	/**
	 * manejador del wizard
	 * @param event
	 * @return
	 */
	public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }


	/**
	 * @return the tiposTramites
	 */
	public List<TipoTramite> getTiposTramites() {
		return tiposTramites;
	}


	/**
	 * @param tiposTramites the tiposTramites to set
	 */
	public void setTiposTramites(List<TipoTramite> tiposTramites) {
		this.tiposTramites = tiposTramites;
	}

	/**
	 * @return the alcaldiaMunicipio
	 */
	public Municipio getAlcaldiaMunicipio() {
		return alcaldiaMunicipio;
	}

	/**
	 * @param alcaldiaMunicipio the alcaldiaMunicipio to set
	 */
	public void setAlcaldiaMunicipio(Municipio alcaldiaMunicipio) {
		this.alcaldiaMunicipio = alcaldiaMunicipio;
	}
	
	
	
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}



	/**
	 * @return the tipoTramite
	 */
	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}

	/**
	 * @param tipoTramite the tipoTramite to set
	 */
	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}
