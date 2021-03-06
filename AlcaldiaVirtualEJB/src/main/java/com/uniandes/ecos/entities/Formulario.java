package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "FORMULARIOS". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="FORMULARIOS")
@NamedQueries({
	@NamedQuery(name="Formulario.findAll", query="SELECT f FROM Formulario f"),
	@NamedQuery(name="Formulario.findByNombre", query="SELECT f FROM Formulario f WHERE UPPER(f.nombre) = :nombre"),
	@NamedQuery(name="Formulario.findByNombreLike", query="SELECT f FROM Formulario f WHERE UPPER(f.nombre) like :nombre")
})
@NamedQuery(name="Formulario.findAll", query="SELECT f FROM Formulario f")
public class Formulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FORMULARIOS_FORMULARIOID_GENERATOR", sequenceName="SEQ_FORMULARIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORMULARIOS_FORMULARIOID_GENERATOR")
	@Column(name="FORMULARIO_ID")
	private long formularioId;
	
	private String nombre;

	//bi-directional many-to-one association to CampoFormulario
	@OneToMany(mappedBy="formulario", cascade = CascadeType.ALL)
	@OrderBy("posicion ASC")
	private List<CampoFormulario> camposFormularios;

	//bi-directional many-to-one association to DocumentoRequerido
	@OneToMany(mappedBy="formulario")
	private List<DocumentoRequerido> documentosRequeridos;

	public Formulario() {
	}

	public long getFormularioId() {
		return this.formularioId;
	}

	public void setFormularioId(long formularioId) {
		this.formularioId = formularioId;
	}

	public List<CampoFormulario> getCamposFormularios() {
		return this.camposFormularios;
	}

	public void setCamposFormularios(List<CampoFormulario> camposFormularios) {
		this.camposFormularios = camposFormularios;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}

	public CampoFormulario addCamposFormulario(CampoFormulario camposFormulario) {
		getCamposFormularios().add(camposFormulario);
		camposFormulario.setFormulario(this);

		return camposFormulario;
	}

	public CampoFormulario removeCamposFormulario(CampoFormulario camposFormulario) {
		getCamposFormularios().remove(camposFormulario);
		camposFormulario.setFormulario(null);

		return camposFormulario;
	}

	public List<DocumentoRequerido> getDocumentosRequeridos() {
		return this.documentosRequeridos;
	}

	public void setDocumentosRequeridos(List<DocumentoRequerido> documentosRequeridos) {
		this.documentosRequeridos = documentosRequeridos;
	}

	public DocumentoRequerido addDocumentosRequerido(DocumentoRequerido documentosRequerido) {
		getDocumentosRequeridos().add(documentosRequerido);
		documentosRequerido.setFormulario(this);

		return documentosRequerido;
	}

	public DocumentoRequerido removeDocumentosRequerido(DocumentoRequerido documentosRequerido) {
		getDocumentosRequeridos().remove(documentosRequerido);
		documentosRequerido.setFormulario(null);

		return documentosRequerido;
	}

}