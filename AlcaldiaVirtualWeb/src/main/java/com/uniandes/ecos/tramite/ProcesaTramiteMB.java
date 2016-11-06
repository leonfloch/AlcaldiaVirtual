package com.uniandes.ecos.tramite;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * MBean para la pantalla de procesamiento de trámites por parte de los funcionarios. 
 * @author Juan Carlos Albarracín. 
 *
 */
@ViewScoped
@ManagedBean
public class ProcesaTramiteMB extends BaseMBean{

	/** Serial de la clase. */
	private static final long serialVersionUID = 1L;

	/** Inyección de dependencia con fachadas de parametrización.  */
	@Inject
	private IProcesadorTramitesFacade procesadorTramitesFacade;

	/** Listas en pantalla.	 */
	private List<Tramite> lstTramitesAProcesar;

	/** Dashboard. */
	private Dashboard dashboard;

	/**
	 * Constructor de la clase. 
	 */
	public ProcesaTramiteMB() {
		
	}

	/**
	 * Incializador de variables del bean
	 */
	@PostConstruct
	public void init(){
		try {
			this.lstTramitesAProcesar = procesadorTramitesFacade.obtenerTramites(2);
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
		}
		
		//Se arma dashboard si hay trámites a procesar
		if (this.lstTramitesAProcesar != null && this.lstTramitesAProcesar.size() > 0) {
			armarDashboard();
		}

	}
	
	/**
	 * Arma el dashboard con los trámitea a procesar.
	 */
	private void armarDashboard(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Application application = fc.getApplication();


		dashboard = (Dashboard) application.createComponent(fc, "org.primefaces.component.Dashboard", "org.prime.component.DashboardRenderer");
		dashboard.setId("dashboard");

		DashboardModel model = new DefaultDashboardModel();
		DashboardColumn columnCreados = new DefaultDashboardColumn();
		DashboardColumn columnProceso = new DefaultDashboardColumn();
		DashboardColumn columnFinalizados = new DefaultDashboardColumn();
		DashboardColumn columnRechazados = new DefaultDashboardColumn();
		model.addColumn(columnCreados);
		model.addColumn(columnProceso);
		model.addColumn(columnFinalizados);
		model.addColumn(columnRechazados);
		dashboard.setModel(model);
		
		for (Tramite tramite : this.lstTramitesAProcesar) {
			Panel panel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
			panel.setId("t-"+tramite.getTramiteId());
			panel.setHeader("Trámite número:" + tramite.getTramiteId());
			panel.setClosable(false);
			panel.setToggleable(false);

			dashboard.getChildren().add(panel);
			if (Constantes.ESTADO_CREADO.equals(tramite.getEstado())) {
				columnCreados.addWidget(panel.getId());
			}else if (Constantes.ESTADO_PROCESO.equals(tramite.getEstado())){
				columnProceso.addWidget(panel.getId());
			}else if (Constantes.ESTADO_FINALIZADO.equals(tramite.getEstado())){
				columnFinalizados.addWidget(panel.getId());
			}else if (Constantes.ESTADO_RECHAZADO.equals(tramite.getEstado())){
				columnRechazados.addWidget(panel.getId());
			}
			
			HtmlOutputText text = new HtmlOutputText();
			text.setId("t"+tramite.getTramiteId());
			text.setValue(tramite.getTiposTramite().getNombre());

			panel.getChildren().add(text);
		}

	}
	
	/**
	 * Manejador de eventos del dashboard. 
	 * @param event
	 */
	public void cambiarEstado(DashboardReorderEvent event) {
		//Se obtiene el id del trámite
		String widgetId = event.getWidgetId();
		String[] parts = widgetId.split("-");
		String part1 = parts[0]; 
		String tramiteId = parts[1];
		
		//Se obtiene el estado al que se desea cambiar
		String estado = null;
		switch (event.getColumnIndex()) {
		case 0:
			estado = Constantes.ESTADO_CREADO;
			break;
		case 1:
			estado = Constantes.ESTADO_PROCESO;
			break;
		case 2:
			estado = Constantes.ESTADO_FINALIZADO;
			break;
		case 3:
			estado = Constantes.ESTADO_RECHAZADO;
			break;
		default:
			break;
		}
		
		//Se cambia el estado del trámite
		try {
			this.procesadorTramitesFacade.cambiarEstadoTramite(Long.valueOf(tramiteId), estado);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
		}
	}

	/**
	 * @return the dashboard
	 */
	public Dashboard getDashboard() {
		return dashboard;
	}

	/**
	 * @param dashboard the dashboard to set
	 */
	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	
}
