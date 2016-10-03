/**
 * 
 */
package com.uniandes.ecos.tramite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.seguridad.ArchivoTramiteMBean;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * mbean encargado de realizar el proceso de tramites para los 
 * ciudadanos
 * @author Leonardo Valbuena 
 *
 */
@ViewScoped
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
	 * Represental la fachada de parametrizacion
	 */
	@Inject
	private IParametrizacionFacade parametrizacionFacade;
	
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
		
		this.tramite = (Tramite)this.obtenerVariableSesion(Constantes.SESION_TRAMITE);
		if (tramite != null) {
			tipoTramite = tramite.getTiposTramite();
			this.removerVariableSesion(Constantes.SESION_TRAMITE);
		}
	}
	
	/**
	 * Inicia un tramite con el tipo de tramite
	 * seleccionado
	 * @return
	 */
	public String iniciarTramite() {
		tramite = new Tramite();
		tramite.setTiposTramite(tipoTramite);
		tramite.setTramiteId(generaIdTramite());
		this.adicionarVariableSesion(Constantes.SESION_TRAMITE, tramite);
		return RutasApp.CREAR_TRAMITE;
	}
	
	/**
	 * Se encarga de generar un codigo de tramite
	 * @return
	 */
	private long generaIdTramite() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String[] diaHora = timeStamp.split("_");
		return Long.parseLong(diaHora[0] + diaHora[1]);		
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
	 * Tramita el tiempo tramite seleccionado
	 */
	public String tramitar() {		
		String redirect = null;
		try {
			tramite.setEstado(Constantes.ACTIVO);
			tramite.setFechaSolicitud(new Date());
			tramite.setMunicipio(alcaldiaMunicipio);			
			tramite.setNombre(tipoTramite.getNombre());
			tramite.setUsuariosCiudadano(parametrizacionFacade.obtenerCiudadano(
					this.getSesion().getPersona().getNumIdentificacion()));
			//TODO pendiente adjuntar los documentos
			//tramite.setDocumentosTramites(documentosTramites);			
			
			procesadorTramitesFacade.crearTramite(tramite);		
			this.adicionarVariableSesion(Constantes.SESION_TRAMITE_ID, tramite.getTramiteId());
			redirect = RutasApp.CONFIRMACION_TRAMITE;
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
		return redirect;
		
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
