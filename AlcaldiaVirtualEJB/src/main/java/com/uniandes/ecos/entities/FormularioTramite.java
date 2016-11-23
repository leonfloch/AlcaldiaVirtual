package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the FORMULARIOS_TRAMITE database table.
 * 
 */
@Entity
@Table(name="FORMULARIOS_TRAMITE")
@NamedQuery(name="FormularioTramite.findAll", query="SELECT f FROM FormularioTramite f")
public class FormularioTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FORMULARIOS_TRAMITE_FORMULARIOID_GENERATOR", sequenceName="SEQ_FORM_TRAMITE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORMULARIOS_TRAMITE_FORMULARIOID_GENERATOR")
	@Column(name="FORMULARIO_ID")
	private long formularioId;

	private String nombre;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="TRAMITE_ID")
	private Tramite tramite;

	//bi-directional many-to-one association to CampoFormularioTramite
	@OneToMany(mappedBy="formulariosTramite")
	private List<CampoFormularioTramite> camposFormularioTramites;

	public FormularioTramite() {
	}

	public long getFormularioId() {
		return this.formularioId;
	}

	public void setFormularioId(long formularioId) {
		this.formularioId = formularioId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<CampoFormularioTramite> getCamposFormularioTramites() {
		if (camposFormularioTramites == null) {
			camposFormularioTramites = new ArrayList<CampoFormularioTramite>();
		}
		return this.camposFormularioTramites;
	}

	public void setCamposFormularioTramites(List<CampoFormularioTramite> camposFormularioTramites) {
		this.camposFormularioTramites = camposFormularioTramites;
	}

	public CampoFormularioTramite addCamposFormularioTramite(CampoFormularioTramite camposFormularioTramite) {
		getCamposFormularioTramites().add(camposFormularioTramite);
		camposFormularioTramite.setFormulariosTramite(this);

		return camposFormularioTramite;
	}

	public CampoFormularioTramite removeCamposFormularioTramite(CampoFormularioTramite camposFormularioTramite) {
		getCamposFormularioTramites().remove(camposFormularioTramite);
		camposFormularioTramite.setFormulariosTramite(null);

		return camposFormularioTramite;
	}

}