package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "ESTADOS_TRAMITE". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="ESTADOS_TRAMITE")
@NamedQuery(name="EstadoTramite.findAll", query="SELECT e FROM EstadoTramite e")
public class EstadoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ESTADO_ID")
	private long estadoId;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to CambioEstadoTramite
	@OneToMany(mappedBy="estadosTramite")
	private List<CambioEstadoTramite> cambiosEstadoTramites;

	public EstadoTramite() {
	}

	public long getEstadoId() {
		return this.estadoId;
	}

	public void setEstadoId(long estadoId) {
		this.estadoId = estadoId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CambioEstadoTramite> getCambiosEstadoTramites() {
		return this.cambiosEstadoTramites;
	}

	public void setCambiosEstadoTramites(List<CambioEstadoTramite> cambiosEstadoTramites) {
		this.cambiosEstadoTramites = cambiosEstadoTramites;
	}

	public CambioEstadoTramite addCambiosEstadoTramite(CambioEstadoTramite cambiosEstadoTramite) {
		getCambiosEstadoTramites().add(cambiosEstadoTramite);
		cambiosEstadoTramite.setEstadosTramite(this);

		return cambiosEstadoTramite;
	}

	public CambioEstadoTramite removeCambiosEstadoTramite(CambioEstadoTramite cambiosEstadoTramite) {
		getCambiosEstadoTramites().remove(cambiosEstadoTramite);
		cambiosEstadoTramite.setEstadosTramite(null);

		return cambiosEstadoTramite;
	}

}