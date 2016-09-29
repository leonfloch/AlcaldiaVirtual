package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "DOCUMENTOS_REQUERIDOS". 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="DOCUMENTOS_REQUERIDOS")
@NamedQuery(name="DocumentoRequerido.findAll", query="SELECT d FROM DocumentoRequerido d")
public class DocumentoRequerido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCUMENTOS_REQUERIDOS_DOCREQUERIDOID_GENERATOR", sequenceName="SEQ_DOC_PLANTILLA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENTOS_REQUERIDOS_DOCREQUERIDOID_GENERATOR")
	@Column(name="DOC_REQUERIDO_ID")
	private long docRequeridoId;

	private String descripcion;

	@Column(name="NOMBRE_ARCHIVO")
	private String nombreArchivo;

	@Column(name="NOMBRE_DOCUMENTO")
	private String nombreDocumento;

	private String ruta;
	
	@Transient
	private boolean estadoUpload;
	

	//bi-directional many-to-one association to DocsXTipoTramite
	@OneToMany(mappedBy="documentosRequerido")
	private List<DocsXTipoTramite> docsXTipoTramites;

	//bi-directional many-to-one association to Formulario
	@ManyToOne
	@JoinColumn(name="FORMULARIO_ID")
	private Formulario formulario;

	public DocumentoRequerido() {
	}

	public long getDocRequeridoId() {
		return this.docRequeridoId;
	}

	public void setDocRequeridoId(long docRequeridoId) {
		this.docRequeridoId = docRequeridoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreDocumento() {
		return this.nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public List<DocsXTipoTramite> getDocsXTipoTramites() {
		return this.docsXTipoTramites;
	}

	public void setDocsXTipoTramites(List<DocsXTipoTramite> docsXTipoTramites) {
		this.docsXTipoTramites = docsXTipoTramites;
	}

	public DocsXTipoTramite addDocsXTipoTramite(DocsXTipoTramite docsXTipoTramite) {
		getDocsXTipoTramites().add(docsXTipoTramite);
		docsXTipoTramite.setDocumentosRequerido(this);

		return docsXTipoTramite;
	}

	public DocsXTipoTramite removeDocsXTipoTramite(DocsXTipoTramite docsXTipoTramite) {
		getDocsXTipoTramites().remove(docsXTipoTramite);
		docsXTipoTramite.setDocumentosRequerido(null);

		return docsXTipoTramite;
	}

	public Formulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}
	
	public boolean isEstadoUpload() {
		return estadoUpload;
	}

	public void setEstadoUpload(boolean estadoUpload) {
		this.estadoUpload = estadoUpload;
	}

}