package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * Clase de persistencia para la tabla "PERSONAS". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="PERSONAS")
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="NUM_IDENTIFICACION")
	private long numIdentificacion;

	private String apellidos;

	private String direccion;

	private String email;

	private String nombres;

	private BigDecimal telefono;

	@Column(name="TIPO_IDENTIFICACION")
	private String tipoIdentificacion;

	//bi-directional many-to-one association to UsuariosCiudadano
	@OneToMany(mappedBy="persona")
	private List<UsuariosCiudadano> usuariosCiudadanos;

	//bi-directional many-to-one association to UsuariosFuncionario
	@OneToMany(mappedBy="persona")
	private List<UsuariosFuncionario> usuariosFuncionarios;

	public Persona() {
	}

	public long getNumIdentificacion() {
		return this.numIdentificacion;
	}

	public void setNumIdentificacion(long numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public BigDecimal getTelefono() {
		return this.telefono;
	}

	public void setTelefono(BigDecimal telefono) {
		this.telefono = telefono;
	}

	public String getTipoIdentificacion() {
		return this.tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public List<UsuariosCiudadano> getUsuariosCiudadanos() {
		return this.usuariosCiudadanos;
	}

	public void setUsuariosCiudadanos(List<UsuariosCiudadano> usuariosCiudadanos) {
		this.usuariosCiudadanos = usuariosCiudadanos;
	}

	public UsuariosCiudadano addUsuariosCiudadano(UsuariosCiudadano usuariosCiudadano) {
		getUsuariosCiudadanos().add(usuariosCiudadano);
		usuariosCiudadano.setPersona(this);

		return usuariosCiudadano;
	}

	public UsuariosCiudadano removeUsuariosCiudadano(UsuariosCiudadano usuariosCiudadano) {
		getUsuariosCiudadanos().remove(usuariosCiudadano);
		usuariosCiudadano.setPersona(null);

		return usuariosCiudadano;
	}

	public List<UsuariosFuncionario> getUsuariosFuncionarios() {
		return this.usuariosFuncionarios;
	}

	public void setUsuariosFuncionarios(List<UsuariosFuncionario> usuariosFuncionarios) {
		this.usuariosFuncionarios = usuariosFuncionarios;
	}

	public UsuariosFuncionario addUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
		getUsuariosFuncionarios().add(usuariosFuncionario);
		usuariosFuncionario.setPersona(this);

		return usuariosFuncionario;
	}

	public UsuariosFuncionario removeUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
		getUsuariosFuncionarios().remove(usuariosFuncionario);
		usuariosFuncionario.setPersona(null);

		return usuariosFuncionario;
	}
	
	/**
	 * 
	 * @return
	 */
	@Transient
	public String getInformacion() {
		return this.nombres + " " + this.apellidos;
	}

}