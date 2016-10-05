package com.uniandes.ecos.seguridad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.dtos.DocumentoTramiteDto;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.util.NegocioException;

@ViewScoped
@ManagedBean
public class ArchivoTramiteMBean extends BaseMBean {
	
	@Inject
	private IProcesadorTramitesFacade tramitesFacade;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3232964861506847360L;
	private static Logger log = Logger.getLogger(ArchivoTramiteMBean.class.getName());
	
	private long tramiteId;
	private List<DocumentoTramiteDto> listaDocumentos;
	private DocumentoTramiteDto documentoSeleccionado;
	private StreamedContent archivo;

	/**
	 * 
	 * @param event
	 */
	public void uploadFileListener(FileUploadEvent event) {
		
		try {			
			listaDocumentos = tramitesFacade.cargarArchivoTramite(tramiteId, event.getFile().getFileName(), event.getFile().getInputstream());
			adicionarMensajeDefinido('I', "archivoCargadoExito");
		} catch (NegocioException | IOException e) {
			adicionarMensajeDefinido('E', "errorServidor");
			log.log(Level.SEVERE, e.getMessage());
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public StreamedContent getArchivo(){
		System.out.println("Archivo seleccionado: "+documentoSeleccionado.getRuta());
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(documentoSeleccionado.getArchivo());
			archivo = new DefaultStreamedContent(inputStream, null, documentoSeleccionado.getArchivo().getName());
		} catch (FileNotFoundException e) {
			adicionarMensajeDefinido('E', "errorServidor");
			log.log(Level.SEVERE, e.getMessage());
		}
		return archivo;
	}

	public long getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(long tramiteId) {
		this.tramiteId = tramiteId;
	}

	public List<DocumentoTramiteDto> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<DocumentoTramiteDto> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public DocumentoTramiteDto getDocumentoSeleccionado() {
		return documentoSeleccionado;
	}

	public void setDocumentoSeleccionado(DocumentoTramiteDto documentoSeleccionado) {
		this.documentoSeleccionado = documentoSeleccionado;
	}
	
	

}
