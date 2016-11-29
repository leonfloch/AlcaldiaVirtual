/**
 * 
 */
package com.uniandes.ecos.comun;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import com.uniandes.ecos.entities.CambioEstadoTramite;
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
@SessionScoped
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
	
	
	
	//--------------------------------------------------------------------
	// HISTORIAL TRAMITE
	//--------------------------------------------------------------------
	private TimelineModel model;    
    private boolean selectable = true;  
    private boolean zoomable = true;  
    private boolean moveable = true;  
    private boolean stackEvents = true;  
    private String eventStyle = "box";  
    private boolean axisOnTop;  
    private boolean showCurrentTime = true;  
    private boolean showNavigation = false;
    
    private Tramite tramiteHistoria;
    private CambioEstadoTramite cambioEstadoSelec;
    //--------------------------------------------------------------------
  	// FIN HISTORIAL TRAMITE
  	//--------------------------------------------------------------------			

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		this.obtenerTramites();		
	}
	
	public boolean isOcultarPanel() {
		return !this.isCiudadano();
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
	
	
	//--------------------------------------------------------------------
	// HISTORIAL TRAMITE
	//--------------------------------------------------------------------
	
	/**
	 * 
	 * @return
	 */
	public String verHistoriaTramite() {
		
		model = new TimelineModel();  		   
        
		for (CambioEstadoTramite cambio : tramiteHistoria.getCambiosEstadoTramites()) {			
			model.add(new TimelineEvent(cambio, cambio.getFecha()));
		}
		return RutasApp.HISTORIA_TRAMITE;
	}
	
	
	/**
	 * 
	 * @param e
	 */
	public void onSelect(TimelineSelectEvent e) {  
        TimelineEvent timelineEvent = e.getTimelineEvent();                  
        cambioEstadoSelec = (CambioEstadoTramite)timelineEvent.getData(); 
    }  
   
    public TimelineModel getModel() {  
        return model;  
    }  
   
    public boolean isSelectable() {  
        return selectable;  
    }  
   
    public void setSelectable(boolean selectable) {  
        this.selectable = selectable;  
    }  
   
    public boolean isZoomable() {  
        return zoomable;  
    }  
   
    public void setZoomable(boolean zoomable) {  
        this.zoomable = zoomable;  
    }  
   
    public boolean isMoveable() {  
        return moveable;  
    }  
   
    public void setMoveable(boolean moveable) {  
        this.moveable = moveable;  
    }  
   
    public boolean isStackEvents() {  
        return stackEvents;  
    }  
   
    public void setStackEvents(boolean stackEvents) {  
        this.stackEvents = stackEvents;  
    }  
   
    public String getEventStyle() {  
        return eventStyle;  
    }  
   
    public void setEventStyle(String eventStyle) {  
        this.eventStyle = eventStyle;  
    }  
   
    public boolean isAxisOnTop() {  
        return axisOnTop;  
    }  
   
    public void setAxisOnTop(boolean axisOnTop) {  
        this.axisOnTop = axisOnTop;  
    }  
   
    public boolean isShowCurrentTime() {  
        return showCurrentTime;  
    }  
   
    public void setShowCurrentTime(boolean showCurrentTime) {  
        this.showCurrentTime = showCurrentTime;  
    }  
   
    public boolean isShowNavigation() {  
        return showNavigation;  
    }  
   
    public void setShowNavigation(boolean showNavigation) {  
        this.showNavigation = showNavigation;  
    }  
    
    /**
	 * @return the cambioEstadoSelec
	 */
	public CambioEstadoTramite getCambioEstadoSelec() {
		return cambioEstadoSelec;
	}

	/**
	 * @param cambioEstadoSelec the cambioEstadoSelec to set
	 */
	public void setCambioEstadoSelec(CambioEstadoTramite cambioEstadoSelec) {
		this.cambioEstadoSelec = cambioEstadoSelec;
	}
    
    //--------------------------------------------------------------------
  	// FIN HISTORIAL TRAMITE
  	//--------------------------------------------------------------------

	/**
	 * @return the tramiteHistoria
	 */
	public Tramite getTramiteHistoria() {
		return tramiteHistoria;
	}

	/**
	 * @param tramiteHistoria the tramiteHistoria to set
	 */
	public void setTramiteHistoria(Tramite tramiteHistoria) {
		this.tramiteHistoria = tramiteHistoria;
	}

}
