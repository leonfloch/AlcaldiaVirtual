package com.uniandes.ecos.dtos;

import java.io.File;

/**
 * 
 * @author Daniel
 *
 */
public class DocumentoTramiteDto {

	private long consecutivo;

	private String nombre;

	private String observacion;

	private String origen;

	private String ruta;

	private long tramiteId;
	
	private File archivo;

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

	public long getTramiteId() {
		return tramiteId;
	}

	public void setTramiteId(long tramiteId) {
		this.tramiteId = tramiteId;
	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	

}
