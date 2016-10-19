package com.uniandes.ecos.tramite;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import com.uniandes.ecos.comun.BaseMBean;

/**
 * MBean para la pantalla de procesamiento de trámites por parte de los funcionarios. 
 * @author Juan Carlos Albarracín. 
 *
 */
@ViewScoped
@ManagedBean
public class ProcesaTramiteMB extends BaseMBean{

	private Dashboard dashboard;

	public ProcesaTramiteMB() {

		FacesContext fc = FacesContext.getCurrentInstance();
		Application application = fc.getApplication();


		dashboard = (Dashboard) application.createComponent(fc, "org.primefaces.component.Dashboard", "org.prime.component.DashboardRenderer");
		dashboard.setId("dashboard");

		DashboardModel model = new DefaultDashboardModel();
		DashboardColumn column1 = new DefaultDashboardColumn();
		column1.addWidget("sdadsasdasd");
		DashboardColumn column2 = new DefaultDashboardColumn();
		model.addColumn(column1);
		model.addColumn(column2);
		dashboard.setModel(model);

		List<String> actividades = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			actividades.add("Task" + i);
		}
		int i = 0;
		for (String act : actividades) {
			
			Panel panel = (Panel) application.createComponent(fc, "org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
			panel.setId(act);
			panel.setHeader("Trámite número: 2956832" + ++i);
			panel.setClosable(true);
			panel.setToggleable(true);

			dashboard.getChildren().add(panel);
			column1.addWidget(panel.getId());
			HtmlOutputText text = new HtmlOutputText();
			text.setId("t"+act);
			text.setValue("Descripción del trámite.");

			panel.getChildren().add(text);
		}

	}

	public Dashboard getDashboard() {
		return dashboard;
	}

	public void setDashboard(Dashboard dashboard) {
		this.dashboard = dashboard;
	}
	
	

}
