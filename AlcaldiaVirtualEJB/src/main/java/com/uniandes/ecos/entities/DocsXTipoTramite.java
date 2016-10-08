package com.uniandes.ecos.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * Clase de persistencia para la tabla "DOCS_X_TIPO_TRAMITE". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="DOCS_X_TIPO_TRAMITE")
@NamedQuery(name="DocsXTipoTramite.findAll", query="SELECT d FROM DocsXTipoTramite d")
public class DocsXTipoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCS_X_TIPO_TRAMITE_CONSECUTIVO_GENERATOR", sequenceName="SEQ_DOC_TIPO_TRAMITE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCS_X_TIPO_TRAMITE_CONSECUTIVO_GENERATOR")
	private long consecutivo;

	private String estado;

	//bi-directional many-to-one association to DocumentoRequerido
	@ManyToOne
	@JoinColumn(name="DOC_REQUERIDO_ID")
	private DocumentoRequerido documentosRequerido;

	//bi-directional many-to-one association to TipoTramite
	@ManyToOne
	@JoinColumn(name="TIPO_TRAMITE_ID")
	private TipoTramite tiposTramite;
	
	@Transient
	private boolean estadoUpload;

	

	public DocsXTipoTramite() {
	}

	public long getConsecutivo() {
		return this.consecutivo;
	}

	public void setConsecutivo(long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public DocumentoRequerido getDocumentosRequerido() {
		return this.documentosRequerido;
	}

	public void setDocumentosRequerido(DocumentoRequerido documentosRequerido) {
		this.documentosRequerido = documentosRequerido;
	}

	public TipoTramite getTiposTramite() {
		return this.tiposTramite;
	}

	public void setTiposTramite(TipoTramite tiposTramite) {
		this.tiposTramite = tiposTramite;
	}
	
	public boolean isEstadoUpload() {
		return estadoUpload;
	}

	public void setEstadoUpload(boolean estadoUpload) {
		this.estadoUpload = estadoUpload;
	}

}