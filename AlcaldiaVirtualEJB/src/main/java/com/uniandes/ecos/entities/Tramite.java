package com.uniandes.ecos.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.uniandes.ecos.util.Constantes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Clase de persistencia para la tabla "TRAMITES". 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="TRAMITES")
@Cacheable(false)
@NamedQueries({
	@NamedQuery(name="Tramite.findAll", query="SELECT f FROM Tramite f"),
	@NamedQuery(name="Tramite.findByMunicipio", query="SELECT f FROM Tramite f WHERE f.municipio.municipioId = :municipioId"),
	@NamedQuery(name="Tramite.findByCiudadano", query="SELECT f FROM Tramite f WHERE f.municipio.municipioId = :municipioId AND f.usuariosCiudadano.usuario = :ciudadanoId")
})
public class Tramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TRAMITE_ID")
	private long tramiteId;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_RESPUESTA")
	private Date fechaRespuesta;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_SOLICITUD")
	private Date fechaSolicitud;

	private String nombre;

	private String observaciones;
	
	@Transient
	private String estadoString;

	//bi-directional many-to-one association to CambioEstadoTramite
	@OneToMany(mappedBy="tramite")
	private List<CambioEstadoTramite> cambiosEstadoTramites;

	//bi-directional many-to-one association to DocumentoTramite
	@OneToMany(mappedBy="tramite", cascade = CascadeType.PERSIST)
	private List<DocumentoTramite> documentosTramites;
	
	//bi-directional many-to-one association to DocumentoTramite
	@OneToMany(mappedBy="tramite", cascade = CascadeType.PERSIST)
	private List<FormularioTramite> formulariosTramites;

	//bi-directional many-to-one association to Pago
	@OneToMany(mappedBy="tramite")
	private List<Pago> pagos;

	//bi-directional many-to-one association to Municipio
	@ManyToOne
	@JoinColumn(name="MUNICIPIO_ID")
	private Municipio municipio;

	//bi-directional many-to-one association to TipoTramite
	@ManyToOne
	@JoinColumn(name="TIPO_TRAMITE_ID")
	private TipoTramite tiposTramite;

	//bi-directional many-to-one association to UsuariosCiudadano
	@ManyToOne
	@JoinColumn(name="USUARIO_CIUDADANO")
	private UsuariosCiudadano usuariosCiudadano;

	public Tramite() {
	}

	public long getTramiteId() {
		return this.tramiteId;
	}

	public void setTramiteId(long tramiteId) {
		this.tramiteId = tramiteId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaRespuesta() {
		return this.fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<CambioEstadoTramite> getCambiosEstadoTramites() {
		return this.cambiosEstadoTramites;
	}

	public void setCambiosEstadoTramites(List<CambioEstadoTramite> cambiosEstadoTramites) {
		this.cambiosEstadoTramites = cambiosEstadoTramites;
	}

	public CambioEstadoTramite addCambiosEstadoTramite(CambioEstadoTramite cambiosEstadoTramite) {
		getCambiosEstadoTramites().add(cambiosEstadoTramite);
		cambiosEstadoTramite.setTramite(this);

		return cambiosEstadoTramite;
	}

	public CambioEstadoTramite removeCambiosEstadoTramite(CambioEstadoTramite cambiosEstadoTramite) {
		getCambiosEstadoTramites().remove(cambiosEstadoTramite);
		cambiosEstadoTramite.setTramite(null);

		return cambiosEstadoTramite;
	}

	public List<DocumentoTramite> getDocumentosTramites() {
		if (documentosTramites == null) {
			documentosTramites = new ArrayList<DocumentoTramite>();
		}
		return this.documentosTramites;
	}

	public void setDocumentosTramites(List<DocumentoTramite> documentosTramites) {
		this.documentosTramites = documentosTramites;
	}
	
	/**
	 * @return the formulariosTramites
	 */
	public List<FormularioTramite> getFormulariosTramites() {
		if (formulariosTramites == null) {
			formulariosTramites = new ArrayList<FormularioTramite>();
		}
		return this.formulariosTramites;
	}

	/**
	 * @param formulariosTramites the formulariosTramites to set
	 */
	public void setFormulariosTramites(List<FormularioTramite> formulariosTramites) {
		this.formulariosTramites = formulariosTramites;
	}

	public DocumentoTramite addDocumentosTramite(DocumentoTramite documentosTramite) {
		getDocumentosTramites().add(documentosTramite);
		documentosTramite.setTramite(this);

		return documentosTramite;
	}

	public DocumentoTramite removeDocumentosTramite(DocumentoTramite documentosTramite) {
		getDocumentosTramites().remove(documentosTramite);
		documentosTramite.setTramite(null);

		return documentosTramite;
	}

	public List<Pago> getPagos() {
		return this.pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public Pago addPago(Pago pago) {
		getPagos().add(pago);
		pago.setTramite(this);

		return pago;
	}

	public Pago removePago(Pago pago) {
		getPagos().remove(pago);
		pago.setTramite(null);

		return pago;
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

	public UsuariosCiudadano getUsuariosCiudadano() {
		return this.usuariosCiudadano;
	}

	public void setUsuariosCiudadano(UsuariosCiudadano usuariosCiudadano) {
		this.usuariosCiudadano = usuariosCiudadano;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEstadoString() {
		String estado = "";
		
		switch (this.estado) {
		case Constantes.ESTADO_CREADO :
			estado = "TRAMITE CREADO";
			break;
		case Constantes.ESTADO_FINALIZADO :
			estado = "TRAMITE FINALIZADO";
			break;
		case Constantes.ESTADO_PROCESO :
			estado = "TRAMITE EN PROCESO";
			break;
		case Constantes.ESTADO_RECHAZADO :
			estado = "TRAMITE RECHAZADO";
			break;	
		default:
			break;
		}		
		return estado;
	}

}