package com.uniandes.ecos.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * Clase de persistencia para la tabla "DOCUMENTOS_TRAMITE". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="DOCUMENTOS_TRAMITE")
@NamedQuery(name="DocumentoTramite.findAll", query="SELECT d FROM DocumentoTramite d")
public class DocumentoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="id_consecutico_seq", sequenceName="SEQ_DOC_TRAMITE", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="id_consecutico_seq")
	private long consecutivo;

	private String nombre;

	private String observacion;

	private String origen;

	private String ruta;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="TRAMITE_ID")
	private Tramite tramite;

	public DocumentoTramite() {
	}

	public long getConsecutivo() {
		return this.consecutivo;
	}

	public void setConsecutivo(long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Tramite getTramite() {
		return this.tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

}