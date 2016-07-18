package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "TipoCampo". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="TIPOS_CAMPO")
@NamedQuery(name="TipoCampo.findAll", query="SELECT t FROM TipoCampo t")
public class TipoCampo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TIPO_CAMPO_ID")
	private long tipoCampoId;

	private String nombre;

	//bi-directional many-to-one association to CampoFormulario
	@OneToMany(mappedBy="tiposCampo")
	private List<CampoFormulario> camposFormularios;

	public TipoCampo() {
	}

	public long getTipoCampoId() {
		return this.tipoCampoId;
	}

	public void setTipoCampoId(long tipoCampoId) {
		this.tipoCampoId = tipoCampoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CampoFormulario> getCamposFormularios() {
		return this.camposFormularios;
	}

	public void setCamposFormularios(List<CampoFormulario> camposFormularios) {
		this.camposFormularios = camposFormularios;
	}

	public CampoFormulario addCamposFormulario(CampoFormulario camposFormulario) {
		getCamposFormularios().add(camposFormulario);
		camposFormulario.setTiposCampo(this);

		return camposFormulario;
	}

	public CampoFormulario removeCamposFormulario(CampoFormulario camposFormulario) {
		getCamposFormularios().remove(camposFormulario);
		camposFormulario.setTiposCampo(null);

		return camposFormulario;
	}

}