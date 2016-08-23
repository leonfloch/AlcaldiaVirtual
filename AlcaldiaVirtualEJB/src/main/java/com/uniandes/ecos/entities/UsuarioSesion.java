package com.uniandes.ecos.entities;

import java.util.List;

import javax.persistence.Id;

public class UsuarioSesion {
	
	
	private String usuario;

	private String contrasenia;

	private String estado;
	
	private List<Tramite> tramites;
	
	private Persona persona;
	
	private Rol role;
	
	private long municipioId;
	
	private Municipio municipio;
	

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the tramites
	 */
	public List<Tramite> getTramites() {
		return tramites;
	}

	/**
	 * @param tramites the tramites to set
	 */
	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the role
	 */
	public Rol getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Rol role) {
		this.role = role;
	}

	/**
	 * @return the municipioId
	 */
	public long getMunicipioId() {
		return municipioId;
	}

	/**
	 * @param municipioId the municipioId to set
	 */
	public void setMunicipioId(long municipioId) {
		this.municipioId = municipioId;
	}

	/**
	 * @return the municipio
	 */
	public Municipio getMunicipio() {
		return municipio;
	}

	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	
	
	
	

}
