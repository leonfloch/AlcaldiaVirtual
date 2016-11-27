package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * Clase de persistencia para la tabla "CAMBIOS_ESTADO_TRAMITE". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="CAMBIOS_ESTADO_TRAMITE")
@NamedQuery(name="CambioEstadoTramite.findAll", query="SELECT c FROM CambioEstadoTramite c")
public class CambioEstadoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAMBIOS_ESTADO_TRAMITE_CAMBIOESTADOID_GENERATOR", sequenceName="SEQ_CAMBIO_ESTADO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CAMBIOS_ESTADO_TRAMITE_CAMBIOESTADOID_GENERATOR")
	@Column(name="CAMBIO_ESTADO_ID")
	private long cambioEstadoId;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA")
	private Date fecha;

	private String observaciones;

	//bi-directional many-to-one association to EstadoTramite
	@ManyToOne
	@JoinColumn(name="ESTADO_ID")
	private EstadoTramite estadosTramite;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="TRAMITE_ID")
	private Tramite tramite;

	//bi-directional many-to-one association to UsuariosFuncionario
	private String usuario;

	public CambioEstadoTramite() {
	}

	public long getCambioEstadoId() {
		return this.cambioEstadoId;
	}

	public void setCambioEstadoId(long cambioEstadoId) {
		this.cambioEstadoId = cambioEstadoId;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFechaFin(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public EstadoTramite getEstadosTramite() {
		return this.estadosTramite;
	}

	public void setEstadosTramite(EstadoTramite estadosTramite) {
		this.estadosTramite = estadosTramite;
	}

	public Tramite getTramite() {
		return this.tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}