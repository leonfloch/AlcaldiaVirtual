package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "USUARIOS_CIUDADANO". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="USUARIOS_CIUDADANO")
@NamedQuery(name="UsuariosCiudadano.findAll", query="SELECT u FROM UsuariosCiudadano u")
public class UsuariosCiudadano extends UsuarioSesion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String usuario;

	private String contrasenia;

	private String estado;
	
	@Transient
	private String contraseniaVal;
	

	//bi-directional many-to-one association to Tramite
	@OneToMany(mappedBy="usuariosCiudadano")
	private List<Tramite> tramites;

	//bi-directional many-to-one association to Persona	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="NUM_IDENTIFICACION")
	private Persona persona;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="ROL_ID")
	private Rol role;

	public UsuariosCiudadano() {
		
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

	public List<Tramite> getTramites() {
		return this.tramites;
	}

	public void setTramites(List<Tramite> tramites) {
		this.tramites = tramites;
	}

	public Tramite addTramite(Tramite tramite) {
		getTramites().add(tramite);
		tramite.setUsuariosCiudadano(this);

		return tramite;
	}

	public Tramite removeTramite(Tramite tramite) {
		getTramites().remove(tramite);
		tramite.setUsuariosCiudadano(null);

		return tramite;
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
	
	/**
	 * @return the contraseniaVal
	 */
	public String getContraseniaVal() {
		return contraseniaVal;
	}

	/**
	 * @param contraseniaVal the contraseniaVal to set
	 */
	public void setContraseniaVal(String contraseniaVal) {
		this.contraseniaVal = contraseniaVal;
	}

}