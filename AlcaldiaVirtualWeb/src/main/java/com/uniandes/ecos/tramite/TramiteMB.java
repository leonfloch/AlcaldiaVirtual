/**
 * 
 */
package com.uniandes.ecos.tramite;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.facade.ProcesadorTramitesFacade;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.seguridad.ArchivoTramiteMBean;
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
	 * 
	 */
	@Inject
	private ArchivoTramiteMBean archivoTramiteMBean;
	
	/**
	 * Representa la fachada que procesa los tramites
	 */
	@Inject
	private IProcesadorTramitesFacade procesadorTramitesFacade;
	
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
	 * Representa el tramite en proceso
	 */
	private Tramite tramite;
	
	

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
		tramite = new Tramite();
		tramite.setTramiteId(generaIdTramite());
		return RutasApp.CREAR_TRAMITE;
	}
	
	/**
	 * Se encarga de generar un codigo de tramite
	 * @return
	 */
	private long generaIdTramite() {
		//TODO generar tramite teniendo encuenta la fecha y la hora
		return 0;
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
	 * Se encarga de subir documentos del tramite al sistema
	 */
	public void subirDocumento(FileUploadEvent event) {
		archivoTramiteMBean.setTramiteId(tramite.getTramiteId());
		archivoTramiteMBean.uploadFileListener(event);
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

	/**
	 * @return the tramite
	 */
	public Tramite getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

}
