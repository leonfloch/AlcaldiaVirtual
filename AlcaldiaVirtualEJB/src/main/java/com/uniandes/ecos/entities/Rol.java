package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * Clase de persistencia para la tabla "ROLES". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="ROLES")
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROL_ID_GENERATOR", sequenceName="SEQ_ROL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROL_ID_GENERATOR")
	@Column(name="ROL_ID")
	private long rolId;

	private String descripcion;

	private String estado;

	private String nombre;

	//bi-directional many-to-one association to PermisoXRol
	@OneToMany(mappedBy="role")
	private List<PermisoXRol> permisosXRols;

	//bi-directional many-to-one association to UsuariosCiudadano
	@OneToMany(mappedBy="role")
	private List<UsuariosCiudadano> usuariosCiudadanos;

	//bi-directional many-to-one association to UsuariosFuncionario
	@OneToMany(mappedBy="role")
	private List<UsuariosFuncionario> usuariosFuncionarios;

	public Rol() {
	}

	public long getRolId() {
		return this.rolId;
	}

	public void setRolId(long rolId) {
		this.rolId = rolId;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PermisoXRol> getPermisosXRols() {
		return this.permisosXRols;
	}

	public void setPermisosXRols(List<PermisoXRol> permisosXRols) {
		this.permisosXRols = permisosXRols;
	}

	public PermisoXRol addPermisosXRol(PermisoXRol permisosXRol) {
		getPermisosXRols().add(permisosXRol);
		permisosXRol.setRole(this);

		return permisosXRol;
	}

	public PermisoXRol removePermisosXRol(PermisoXRol permisosXRol) {
		getPermisosXRols().remove(permisosXRol);
		permisosXRol.setRole(null);

		return permisosXRol;
	}

	public List<UsuariosCiudadano> getUsuariosCiudadanos() {
		return this.usuariosCiudadanos;
	}

	public void setUsuariosCiudadanos(List<UsuariosCiudadano> usuariosCiudadanos) {
		this.usuariosCiudadanos = usuariosCiudadanos;
	}

	public UsuariosCiudadano addUsuariosCiudadano(UsuariosCiudadano usuariosCiudadano) {
		getUsuariosCiudadanos().add(usuariosCiudadano);
		usuariosCiudadano.setRole(this);

		return usuariosCiudadano;
	}

	public UsuariosCiudadano removeUsuariosCiudadano(UsuariosCiudadano usuariosCiudadano) {
		getUsuariosCiudadanos().remove(usuariosCiudadano);
		usuariosCiudadano.setRole(null);

		return usuariosCiudadano;
	}

	public List<UsuariosFuncionario> getUsuariosFuncionarios() {
		return this.usuariosFuncionarios;
	}

	public void setUsuariosFuncionarios(List<UsuariosFuncionario> usuariosFuncionarios) {
		this.usuariosFuncionarios = usuariosFuncionarios;
	}

//	public UsuariosFuncionario addUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
//		getUsuariosFuncionarios().add(usuariosFuncionario);
//		usuariosFuncionario.setRole(this);
//
//		return usuariosFuncionario;
//	}
//
//	public UsuariosFuncionario removeUsuariosFuncionario(UsuariosFuncionario usuariosFuncionario) {
//		getUsuariosFuncionarios().remove(usuariosFuncionario);
//		usuariosFuncionario.setRole(null);
//
//		return usuariosFuncionario;
//	}

}