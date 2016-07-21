package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "MUNICIPIOS". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="MUNICIPIOS")
@NamedQuery(name="Municipio.findAll", query="SELECT m FROM Municipio m")
public class Municipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MUNICIPIO_ID")
	private long municipioId;

	private String nombre;

	//bi-directional many-to-one association to Departamento
	@ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	private Departamento departamento;

	//bi-directional many-to-one association to Tramite
	@OneToMany(mappedBy="municipio")
	private List<Tramite> tramites;

	//bi-directional many-to-one association to TramiteXMunicipio
	@OneToMany(mappedBy="municipio")
	private List<TramiteXMunicipio> tramitesXMunicipios;

	public Municipio() {
	}

	public long getMunicipioId() {
		return this.municipioId;
	}

	public void setMunicipioId(long municipioId) {
		this.municipioId = municipioId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

	public Tramite addTramite(Tramite tramite) {
		getTramites().add(tramite);
		tramite.setMunicipio(this);

		return tramite;
	}

	public Tramite removeTramite(Tramite tramite) {
		getTramites().remove(tramite);
		tramite.setMunicipio(null);

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
		tramitesXMunicipio.setMunicipio(this);

		return tramitesXMunicipio;
	}

	public TramiteXMunicipio removeTramitesXMunicipio(TramiteXMunicipio tramitesXMunicipio) {
		getTramitesXMunicipios().remove(tramitesXMunicipio);
		tramitesXMunicipio.setMunicipio(null);

		return tramitesXMunicipio;
	}

//	public UsuariosFuncionario addUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
//		getUsuariosFuncionarios().add(usuariosFuncionario);
//		usuariosFuncionario.setMunicipio(this);
//
//		return usuariosFuncionario;
//	}
//
//	public UsuariosFuncionario removeUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
//		getUsuariosFuncionarios().remove(usuariosFuncionario);
//		usuariosFuncionario.setMunicipio(null);
//
//		return usuariosFuncionario;
//	}

}