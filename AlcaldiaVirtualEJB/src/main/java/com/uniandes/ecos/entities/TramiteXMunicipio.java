package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Clase de persistencia para la tabla "TRAMITES_X_MUNICIPIO". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="TRAMITES_X_MUNICIPIO")
@NamedQuery(name="TramiteXMunicipio.findAll", query="SELECT t FROM TramiteXMunicipio t")
public class TramiteXMunicipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TRAMITES_X_MUNICIPIO_CONSECUTIVO_GENERATOR", sequenceName="SEQ_TRAMITE_X_MUNICIPIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TRAMITES_X_MUNICIPIO_CONSECUTIVO_GENERATOR")
	private long consecutivo;

	private String estado;

	//bi-directional many-to-one association to Municipio
	@ManyToOne
	@JoinColumn(name="MUNICIPIO_ID")
	private Municipio municipio;

	//bi-directional many-to-one association to TipoTramite
	@ManyToOne
	@JoinColumn(name="TIPO_TRAMITE_ID")
	private TipoTramite tiposTramite;

	//bi-directional many-to-one association to UsuariosFuncionario
	@ManyToOne
	@JoinColumn(name="USUARIO_RESPONSABLE")
	private UsuariosFuncionario usuariosFuncionario;

	public TramiteXMunicipio() {
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

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public TipoTramite getTiposTramite() {
		return this.tiposTramite;
	}

	public void setTiposTramite(TipoTramite tiposTramite) {
		this.tiposTramite = tiposTramite;
	}

	public UsuariosFuncionario getUsuariosFuncionario() {
		return this.usuariosFuncionario;
	}

	public void setUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
		this.usuariosFuncionario = usuariosFuncionario;
	}

}