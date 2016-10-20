package com.uniandes.ecos.tramite;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.management.StringValueExp;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
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
		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();
		model.addColumn(column1);
		model.addColumn(column2);
		dashboard.setModel(model);
		
		for (Tramite tramite : this.lstTramitesAProcesar) {
			Panel panel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
			panel.setId("tra"+tramite.getTramiteId());
			panel.setHeader("Trámite número:" + tramite.getTramiteId());
			panel.setClosable(false);
			panel.setToggleable(false);

			dashboard.getChildren().add(panel);
			column1.addWidget(panel.getId());
			HtmlOutputText text = new HtmlOutputText();
			text.setId("t"+tramite.getTramiteId());
			text.setValue(tramite.getTiposTramite().getNombre());

			panel.getChildren().add(text);
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
