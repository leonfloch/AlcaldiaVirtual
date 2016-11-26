package com.uniandes.ecos.tramite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.DocumentoTramite;
import com.uniandes.ecos.entities.FormularioTramite;
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
	
	private List<DocumentoTramite> documentosTramite;
	
	private List<FormularioTramite> formulariosTramite;
	
	private Tramite tramiteSeleccionado;
	
	private DocumentoTramite documentoTramiteSeleccionado;
	
	private FormularioTramite formularioTramiteSeleccionado;
	
	private StreamedContent archivo;
	
	private String observaciones;

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
			String idPanel = "t-"+tramite.getTramiteId();
			if (Constantes.ESTADO_CREADO.equals(tramite.getEstado())) {
				columnCreados.addWidget(idPanel);
			}else if (Constantes.ESTADO_PROCESO.equals(tramite.getEstado())){
				columnProceso.addWidget(idPanel);
			}else if (Constantes.ESTADO_FINALIZADO.equals(tramite.getEstado())){
				columnFinalizados.addWidget(idPanel);
			}else if (Constantes.ESTADO_RECHAZADO.equals(tramite.getEstado())){
				columnRechazados.addWidget(idPanel);
			}
			
		}

	}
	
	/**
	 * Manejador de eventos del dashboard. 
	 * @param event
	 */
	public void cambiarEstado(DashboardReorderEvent event) {
		//Se obtiene el id del trámite
		String tramiteId = event.getWidgetId().split("-")[1];
		
		System.out.println("Cambiar estado de tramite a: "+tramiteId);
		
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
	
	public void cargarDocumentos(){
		try {
			documentosTramite = procesadorTramitesFacade.buscarDocumentosPorTramite(tramiteSeleccionado.getTramiteId());
			formulariosTramite = procesadorTramitesFacade.buscarFormulariosPorTramite(tramiteSeleccionado.getTramiteId());
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.adicionarMensaje(e.getTipo(), e.getMensaje());	
		}
	}
	
	public void prepararDescarga(){
		File file = new File(documentoTramiteSeleccionado.getRuta());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		InputStream stream;
		try {
			stream = new FileInputStream(file);
			archivo = new DefaultStreamedContent(stream,externalContext.getMimeType(file.getName()),file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.adicionarMensaje(Constantes.ERROR, "Se ha presentado un error al descargar el archivo");	
		}
	}
	
	public StreamedContent getArchivo(){
		prepararDescarga();
		return archivo;
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

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the documentosTramite
	 */
	public List<DocumentoTramite> getDocumentosTramite() {
		return documentosTramite;
	}

	/**
	 * @param documentosTramite the documentosTramite to set
	 */
	public void setDocumentosTramite(List<DocumentoTramite> documentosTramite) {
		this.documentosTramite = documentosTramite;
	}

	/**
	 * @return the formulariosTramite
	 */
	public List<FormularioTramite> getFormulariosTramite() {
		return formulariosTramite;
	}

	/**
	 * @param formulariosTramite the formulariosTramite to set
	 */
	public void setFormulariosTramite(List<FormularioTramite> formulariosTramite) {
		this.formulariosTramite = formulariosTramite;
	}

	/**
	 * @return the lstTramitesAProcesar
	 */
	public List<Tramite> getLstTramitesAProcesar() {
		return lstTramitesAProcesar;
	}

	/**
	 * @param lstTramitesAProcesar the lstTramitesAProcesar to set
	 */
	public void setLstTramitesAProcesar(List<Tramite> lstTramitesAProcesar) {
		this.lstTramitesAProcesar = lstTramitesAProcesar;
	}

	/**
	 * @return the tramiteSeleccionado
	 */
	public Tramite getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	/**
	 * @param tramiteSeleccionado the tramiteSeleccionado to set
	 */
	public void setTramiteSeleccionado(Tramite tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	/**
	 * @return the documentoTramiteSeleccionado
	 */
	public DocumentoTramite getDocumentoTramiteSeleccionado() {
		return documentoTramiteSeleccionado;
	}

	/**
	 * @param documentoTramiteSeleccionado the documentoTramiteSeleccionado to set
	 */
	public void setDocumentoTramiteSeleccionado(DocumentoTramite documentoTramiteSeleccionado) {
		this.documentoTramiteSeleccionado = documentoTramiteSeleccionado;
	}

	/**
	 * @return the formularioTramiteSeleccionado
	 */
	public FormularioTramite getFormularioTramiteSeleccionado() {
		return formularioTramiteSeleccionado;
	}

	/**
	 * @param formularioTramiteSeleccionado the formularioTramiteSeleccionado to set
	 */
	public void setFormularioTramiteSeleccionado(FormularioTramite formularioTramiteSeleccionado) {
		this.formularioTramiteSeleccionado = formularioTramiteSeleccionado;
	}
	
	
}
