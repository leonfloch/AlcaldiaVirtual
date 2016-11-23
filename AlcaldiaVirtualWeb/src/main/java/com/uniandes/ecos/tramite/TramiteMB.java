/**
 * 
 */
package com.uniandes.ecos.tramite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.entities.DocsXTipoTramite;
import com.uniandes.ecos.entities.DocumentoRequerido;
import com.uniandes.ecos.entities.DocumentoTramite;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.Tramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.seguridad.ArchivoTramiteMBean;
import com.uniandes.ecos.services.procesador.DocumentosService;
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
	 * Menjo del log
	 */
	private static Logger log = Logger.getLogger(TramiteMB.class.getName());
	
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
	 * Documentos requeridos para el tramite en curso
	 */
	private List<DocumentoRequerido> docRequeridos;
	
	/**
	 * Representa el tramite en proceso
	 */
	private Tramite tramite;		
	
	/** índicel del tab actual en el wizard. */
	private String pasoActual;

	/**
	 * Constructor
	 */
	public TramiteMB() {
		skip = false;
		tipoTramite = new TipoTramite();
		tiposTramites = new ArrayList<TipoTramite>();
		docRequeridos = new ArrayList<DocumentoRequerido>();
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
			
			for (DocsXTipoTramite docXtipo : tipoTramite.getDocsXTipoTramites()) {
				docRequeridos.add(docXtipo.getDocumentosRequerido());
			}
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
	 * 
	 */
	public void agregarObservacion() {
		
		
	}
	
	/**
	 * Se encarga de subir documentos del tramite al sistema
	 */
	public void subirDocumento(FileUploadEvent event) {
		
		try {
			DocumentoRequerido docRequerido = (DocumentoRequerido) event.getComponent().getAttributes().get("docTipo");
			
			
			
			archivoTramiteMBean.setTramiteId(tramite.getTramiteId());
			DocumentoTramiteDto docDto = archivoTramiteMBean.uploadFileListener(event);
			
			
			DocumentoTramite documento = new DocumentoTramite();
			documento.setNombre(docRequerido.getNombreDocumento());
			documento.setObservacion(docRequerido.getObservaciones());
			documento.setRuta(docDto.getRuta() + "\\" + event.getFile().getFileName());
			documento.setOrigen(Constantes.ENTREGADO);
			documento.setTramite(tramite);
			tramite.getDocumentosTramites().add(documento);
			
			docRequerido.setEstadoUpload(true);
			
			//TODO revisar ya que no actualiza la imagen de ok
			int cont = 0;
			for (DocumentoRequerido docReq : docRequeridos) {
				
				if (docReq.getNombreDocumento().equals(docRequerido.getNombreDocumento())) {
					docRequeridos.set(cont, docReq);
					break;
				}
				cont++;
			}
			
			adicionarMensajeDefinido('I', "archivoCargadoExito");
		} catch (NegocioException | IOException e) {
			adicionarMensajeDefinido('E', "errorServidor");
			log.log(Level.SEVERE, e.getMessage());
		}
		
	}
	
	/**
	 * Tramita el tiempo tramite seleccionado
	 */
	public String tramitar() {		
		String redirect = null;
		try {
			this.validarTramite();
			tramite.setEstado(Constantes.ACTIVO);
			tramite.setFechaSolicitud(new Date());
			tramite.setMunicipio(alcaldiaMunicipio);			
			tramite.setNombre(tipoTramite.getNombre());
			tramite.setUsuariosCiudadano(parametrizacionFacade.obtenerCiudadano(
					this.getSesion().getPersona().getNumIdentificacion()));
						
			
			procesadorTramitesFacade.crearTramite(tramite);		
			this.adicionarVariableSesion(Constantes.SESION_TRAMITE_ID, tramite.getTramiteId());
			redirect = RutasApp.CONFIRMACION_TRAMITE;
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
		return redirect;
	}
	
	/**
	 * se encarga de validar el tramite antes de ser enviado
	 * @throws NegocioException
	 */
	private void validarTramite() throws NegocioException {
		
		if (docRequeridos.size() != tramite.getDocumentosTramites().size()) {
			throw new NegocioException(Constantes.ERROR, 0, "Todos los documentos son obligatorios");
		}
		
	}
	
	/**
	 * Previsualiza el formulario dinámico del documento. 
	 */
	public String armarFormulario(Formulario formulario){
		this.adicionarVariableSesion(Constantes.FORMULARIO_DINAMICO, formulario);
		this.adicionarVariableSesion(Constantes.SESION_TRAMITE, this.tramite);
		this.adicionarVariableSesion(Constantes.RUTA_INVOCACION_FORMULARIO, RutasApp.CREAR_TRAMITE);
		this.adicionarVariableSesion("pasoActual", this.pasoActual);
		return RutasApp.FORMULARIO_DINAMICO;
	}
	
	/**
	 * Cancela el tramite
	 * @return
	 */
	public String cancelar() {
		return RutasApp.CONSULTA_TIPO_TRAMITE;
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

	/**
	 * @return the docRequeridos
	 */
	public List<DocumentoRequerido> getDocRequeridos() {
		return docRequeridos;
	}

	/**
	 * @param docRequeridos the docRequeridos to set
	 */
	public void setDocRequeridos(List<DocumentoRequerido> docRequeridos) {
		this.docRequeridos = docRequeridos;
	}

	/**
	 * @return the pasoActual
	 */
	public String getPasoActual() {
		return pasoActual;
	}

	/**
	 * @param pasoActual the pasoActual to set
	 */
	public void setPasoActual(String pasoActual) {
		this.pasoActual = pasoActual;
	}

}
