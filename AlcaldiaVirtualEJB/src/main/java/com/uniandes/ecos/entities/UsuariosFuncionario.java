package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "USUARIOS_FUNCIONARIO". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="USUARIOS_FUNCIONARIO")
@NamedQueries({
	@NamedQuery(name="UsuariosFuncionario.findAll", query="SELECT u FROM UsuariosFuncionario u"),
	@NamedQuery(name="UsuariosFuncionario.findByAlcaldia", query="SELECT u FROM UsuariosFuncionario u WHERE u.municipio.municipioId = :municipioId"),
	@NamedQuery(name="UsuariosFuncionario.findByEstado", query="SELECT u FROM UsuariosFuncionario u WHERE u.municipio.municipioId = :municipioId AND u.estado = :estado")
})
public class UsuariosFuncionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String usuario;

	private String contrasenia;

	private String estado;

	//bi-directional many-to-one association to CambioEstadoTramite
	@OneToMany(mappedBy="usuariosFuncionario")
	private List<CambioEstadoTramite> cambiosEstadoTramites;

	//bi-directional many-to-one association to TramiteXMunicipio
	@OneToMany(mappedBy="usuariosFuncionario")
	private List<TramiteXMunicipio> tramitesXMunicipios;

	//bi-directional many-to-one association to Municipio
	@ManyToOne
	@JoinColumn(name="MUNICIPIO_ID")
	private Municipio municipio;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="NUM_IDENTIFICACION")
	private Persona persona;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="ROL_ID")
	private Rol role;

	public UsuariosFuncionario() {
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<CambioEstadoTramite> getCambiosEstadoTramites() {
		return this.cambiosEstadoTramites;
	}

	public void setCambiosEstadoTramites(List<CambioEstadoTramite> cambiosEstadoTramites) {
		this.cambiosEstadoTramites = cambiosEstadoTramites;
	}

	public CambioEstadoTramite addCambiosEstadoTramite(CambioEstadoTramite cambiosEstadoTramite) {
		getCambiosEstadoTramites().add(cambiosEstadoTramite);
		cambiosEstadoTramite.setUsuariosFuncionario(this);

		return cambiosEstadoTramite;
	}

	public CambioEstadoTramite removeCambiosEstadoTramite(CambioEstadoTramite cambiosEstadoTramite) {
		getCambiosEstadoTramites().remove(cambiosEstadoTramite);
		cambiosEstadoTramite.setUsuariosFuncionario(null);

		return cambiosEstadoTramite;
	}

	public List<TramiteXMunicipio> getTramitesXMunicipios() {
		return this.tramitesXMunicipios;
	}

	public void setTramitesXMunicipios(List<TramiteXMunicipio> tramitesXMunicipios) {
		this.tramitesXMunicipios = tramitesXMunicipios;
	}

	public TramiteXMunicipio addTramitesXMunicipio(TramiteXMunicipio tramitesXMunicipio) {
		getTramitesXMunicipios().add(tramitesXMunicipio);
		tramitesXMunicipio.setUsuariosFuncionario(this);

		return tramitesXMunicipio;
	}

	public TramiteXMunicipio removeTramitesXMunicipio(TramiteXMunicipio tramitesXMunicipio) {
		getTramitesXMunicipios().remove(tramitesXMunicipio);
		tramitesXMunicipio.setUsuariosFuncionario(null);

		return tramitesXMunicipio;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Rol getRole() {
		return this.role;
	}

	public void setRole(Rol role) {
		this.role = role;
	}

}