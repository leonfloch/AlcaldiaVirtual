package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Clase de persistencia para la tabla "PERMISOS_X_ROL". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="PERMISOS_X_ROL")
@NamedQuery(name="PermisoXRol.findAll", query="SELECT p FROM PermisoXRol p")
public class PermisoXRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PermisoXRolPK id;

	private String estado;

	//bi-directional many-to-one association to Funcionalidad
	@ManyToOne
	@JoinColumn(name="FUNCIONALIDAD_ID")
	private Funcionalidad funcionalidade;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="ROL_ID")
	private Rol role;

	public PermisoXRol() {
	}

	public PermisoXRolPK getId() {
		return this.id;
	}

	public void setId(PermisoXRolPK id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Funcionalidad getFuncionalidade() {
		return this.funcionalidade;
	}

	public void setFuncionalidade(Funcionalidad funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public Rol getRole() {
		return this.role;
	}

	public void setRole(Rol role) {
		this.role = role;
	}

}