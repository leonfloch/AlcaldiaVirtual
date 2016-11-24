package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CAMPOS_FORMULARIO_TRAMITE database table.
 * 
 */
@Entity
@Table(name="CAMPOS_FORMULARIO_TRAMITE")
@NamedQuery(name="CampoFormularioTramite.findAll", query="SELECT c FROM CampoFormularioTramite c")
public class CampoFormularioTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAMPOS_FORMULARIO_TRAMITE_CAMPOID_GENERATOR", sequenceName="SEQ_CAMP_FORM_TRAMITE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CAMPOS_FORMULARIO_TRAMITE_CAMPOID_GENERATOR")
	@Column(name="CAMPO_ID")
	private long campoId;

	private String nombre;

	private String valor;

	//bi-directional many-to-one association to FormularioTramite
	@ManyToOne
	@JoinColumn(name="FORMULARIO_ID")
	private FormularioTramite formulariosTramite;

	public CampoFormularioTramite() {
	}

	public long getCampoId() {
		return this.campoId;
	}

	public void setCampoId(long campoId) {
		this.campoId = campoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public FormularioTramite getFormulariosTramite() {
		return this.formulariosTramite;
	}

	public void setFormulariosTramite(FormularioTramite formulariosTramite) {
		this.formulariosTramite = formulariosTramite;
	}

}