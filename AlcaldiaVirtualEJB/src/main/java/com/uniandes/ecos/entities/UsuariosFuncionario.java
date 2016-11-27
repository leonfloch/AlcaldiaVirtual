package com.uniandes.ecos.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.uniandes.ecos.util.Constantes;

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
public class UsuariosFuncionario extends UsuarioSesion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String usuario;

	private String contrasenia;

	private String estado;
	
	@Transient
	private boolean activo;
	
	
//	@Column(name="ROL_ID")
//	private long rolId;
	
	

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="ROL_ID")
	private Rol role;
	
	@ManyToOne
	@JoinColumn(name="MUNICIPIO_ID")
	private Municipio municipio;
	

//	@Column(name="MUNICIPIO_ID")
//	private long municipioId;


	//bi-directional many-to-one association to TramiteXMunicipio
	@OneToMany(mappedBy="usuariosFuncionario")
	private List<TramiteXMunicipio> tramitesXMunicipios;

	//bi-directional many-to-one association to Persona
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="NUM_IDENTIFICACION")
	private Persona persona;
	
	@Transient
	private String contraseniaVal;

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

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
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
	 * @return the activo
	 */
	public boolean isActivo() {	
		if (Constantes.ACTIVO.equalsIgnoreCase(this.estado)) {
			activo = true;
		} else {
			activo = false;
		}
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {		
		if (activo) {
			this.estado = Constantes.ACTIVO;
		} else {
			this.estado = Constantes.INACTIVO;
		}		
		this.activo = activo;
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