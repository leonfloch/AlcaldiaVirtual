package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * Clase de persistencia para la tabla "TIPOS_TRAMITE". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="TIPOS_TRAMITE")
@NamedQueries({
	@NamedQuery(name="TipoTramite.findAll", query="SELECT t FROM TipoTramite t"),
	@NamedQuery(name="TipoTramite.findByNombreLike", query="SELECT t FROM TipoTramite t WHERE UPPER(t.nombre) LIKE :nombre")
})
public class TipoTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOS_TRAMITE_TIPOTRAMITEID_GENERATOR", sequenceName="SEQ_TIPO_TRAMITE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOS_TRAMITE_TIPOTRAMITEID_GENERATOR")
	@Column(name="TIPO_TRAMITE_ID")
	private long tipoTramiteId;

	private String descripcion;

	private String nombre;

	private BigDecimal valor;

	//bi-directional many-to-one association to DocsXTipoTramite
	@OneToMany(mappedBy="tiposTramite")
	private List<DocsXTipoTramite> docsXTipoTramites;

	//bi-directional many-to-one association to Tramite
	@OneToMany(mappedBy="tiposTramite")
	private List<Tramite> tramites;

	//bi-directional many-to-one association to TramiteXMunicipio
	@OneToMany(mappedBy="tiposTramite", cascade = CascadeType.MERGE)
	private List<TramiteXMunicipio> tramitesXMunicipios;

	public TipoTramite() {
	}

	public long getTipoTramiteId() {
		return this.tipoTramiteId;
	}

	public void setTipoTramiteId(long tipoTramiteId) {
		this.tipoTramiteId = tipoTramiteId;
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

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<DocsXTipoTramite> getDocsXTipoTramites() {
		return this.docsXTipoTramites;
	}

	public void setDocsXTipoTramites(List<DocsXTipoTramite> docsXTipoTramites) {
		this.docsXTipoTramites = docsXTipoTramites;
	}

	public DocsXTipoTramite addDocsXTipoTramite(DocsXTipoTramite docsXTipoTramite) {
		getDocsXTipoTramites().add(docsXTipoTramite);
		docsXTipoTramite.setTiposTramite(this);

		return docsXTipoTramite;
	}

	public DocsXTipoTramite removeDocsXTipoTramite(DocsXTipoTramite docsXTipoTramite) {
		getDocsXTipoTramites().remove(docsXTipoTramite);
		docsXTipoTramite.setTiposTramite(null);

		return docsXTipoTramite;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

	public Tramite addTramite(Tramite tramite) {
		getTramites().add(tramite);
		tramite.setTiposTramite(this);

		return tramite;
	}

	public Tramite removeTramite(Tramite tramite) {
		getTramites().remove(tramite);
		tramite.setTiposTramite(null);

		return tramite;
	}

	public List<TramiteXMunicipio> getTramitesXMunicipios() {
		return this.tramitesXMunicipios;
	}

	public void setTramitesXMunicipios(List<TramiteXMunicipio> tramitesXMunicipios) {
		this.tramitesXMunicipios = tramitesXMunicipios;
	}

	public TramiteXMunicipio addTramitesXMunicipio(TramiteXMunicipio tramitesXMunicipio) {
		getTramitesXMunicipios().add(tramitesXMunicipio);
		tramitesXMunicipio.setTiposTramite(this);

		return tramitesXMunicipio;
	}

	public TramiteXMunicipio removeTramitesXMunicipio(TramiteXMunicipio tramitesXMunicipio) {
		getTramitesXMunicipios().remove(tramitesXMunicipio);
		tramitesXMunicipio.setTiposTramite(null);

		return tramitesXMunicipio;
	}

}