package com.uniandes.ecos.parametrizacion;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.DocumentoRequerido;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Clase encargada de funcionar como bean para administrar los documentos
 * requeridos
 * 
 * @author Daniel Arévalo
 *
 */
@ManagedBean
@ViewScoped
public class AdminDocumentoMB extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Servicio de parametrizacion
	 */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;
	@Inject
	private IFormulariosParamService iFormulariosParamService;

	/**
	 * Lista de documentos requeridos
	 */
	private List<DocumentoRequerido> documentosRequeridos;

	/**
	 * indica si se crea un nuevo documento
	 */
	private boolean creacion;

	/**
	 * Documento seleccionado por el usuario
	 */
	private DocumentoRequerido documentoSelecc;

	/**
	 * Formulario seleccionado por el usuario
	 */
	private Formulario formularioSelecc;

	/**
	 * Lista de formularios disponibles
	 */
	private List<Formulario> formularios;

	/**
	 * Se ejecuta inmediatamente despues de acceder al metodo. Se encarga de
	 * iniciar los parametros necesarios para la gestion de documentos
	 * requeridos.
	 */
	@PostConstruct
	public void init() {
		try {
			documentosRequeridos = iParamTramitesFacade.obtenerDocumentosRequeridos();
			formularios = iFormulariosParamService.obtenerFormularios(null);
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Se ejecuta antes de abril el modal de edicion de documentos
	 */
	public void preModal() {
		if (creacion) {
			documentoSelecc = new DocumentoRequerido();
			formularioSelecc = new Formulario();
		} else {
			if (documentoSelecc.getFormulario() != null)
				for (Formulario form : formularios) {
					if (form.getFormularioId() == documentoSelecc.getFormulario().getFormularioId())
						formularioSelecc = form;
				}
		}
	}

	/**
	 * Se engarga de crear o actualizar un documento requerido
	 */
	public void persistirDocumento() {
		try {
			if (formularioSelecc != null) {
				documentoSelecc.setFormulario(formularioSelecc);
			} else {
				documentoSelecc.setFormulario(null);
			}

			iParamTramitesFacade.actualizarDocumentoRequerido(documentoSelecc);
			documentosRequeridos = iParamTramitesFacade.obtenerDocumentosRequeridos();
			this.adicionarMensaje(Constantes.INFO, "Documento Requerido guardado");
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}

	}

	/**
	 * Se encarga de reiniciar los atributos necesarios para la administracion
	 * de documentos requeridos.
	 */
	public void limpiarAtributos() {
		documentoSelecc = new DocumentoRequerido();
	}

	/**
	 * Se encarga de recibir los archivos cargados por el usuario y de enviarlos
	 * al servicio de cargue de archivos
	 * 
	 * @param event
	 */
	public void cargarDocumentoRequerido(FileUploadEvent event) {
		try {
			DocumentoRequerido docReq = iParamTramitesFacade.cargarDocumentoRequerido(event.getFile().getFileName(),
					event.getFile().getInputstream());
			documentoSelecc.setNombreArchivo(docReq.getNombreArchivo());
			documentoSelecc.setRuta(docReq.getRuta());
		} catch (NegocioException ne) {
			RequestContext.getCurrentInstance().execute("PF('docReqWV').reset();");
			this.adicionarMensaje(ne.getTipo(), ne.getMensaje());
		} catch (IOException ioe) {
			this.adicionarMensaje(Constantes.ERROR, ioe.getMessage());
		}
	}

	// GETERS AND SETERS

	/**
	 * @return the documentosRequeridos
	 */
	public List<DocumentoRequerido> getDocumentosRequeridos() {
		return documentosRequeridos;
	}

	/**
	 * @param documentosRequeridos
	 *            the documentosRequeridos to set
	 */
	public void setDocumentosRequeridos(List<DocumentoRequerido> documentosRequeridos) {
		this.documentosRequeridos = documentosRequeridos;
	}

	/**
	 * @return the creacion
	 */
	public boolean isCreacion() {
		return creacion;
	}

	/**
	 * @param creacion
	 *            the creacion to set
	 */
	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
	}

	/**
	 * @return the documentoSelecc
	 */
	public DocumentoRequerido getDocumentoSelecc() {
		return documentoSelecc;
	}

	/**
	 * @param documentoSelecc
	 *            the documentoSelecc to set
	 */
	public void setDocumentoSelecc(DocumentoRequerido documentoSelecc) {
		this.documentoSelecc = documentoSelecc;
	}

	/**
	 * @return the formularios
	 */
	public List<Formulario> getFormularios() {
		return formularios;
	}

	/**
	 * @param formularios
	 *            the formularios to set
	 */
	public void setFormularios(List<Formulario> formularios) {
		this.formularios = formularios;
	}

	/**
	 * @return the formularioSelecc
	 */
	public Formulario getFormularioSelecc() {
		return formularioSelecc;
	}

	/**
	 * @param formularioSelecc
	 *            the formularioSelecc to set
	 */
	public void setFormularioSelecc(Formulario formularioSelecc) {
		this.formularioSelecc = formularioSelecc;
	}

}
