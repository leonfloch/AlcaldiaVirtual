package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "FUNCIONALIDADES". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="FUNCIONALIDADES")
@NamedQuery(name="Funcionalidad.findAll", query="SELECT f FROM Funcionalidad f")
public class Funcionalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FUNCIONALIDADES_FUNCIONALIDADID_GENERATOR", sequenceName="SEQ_FUNCIONALIDAD", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FUNCIONALIDADES_FUNCIONALIDADID_GENERATOR")
	@Column(name="FUNCIONALIDAD_ID")
	private long funcionalidadId;

	private String descripcion;

	private String nombre;
	
	private Long padre;
	
	private String icono;

	

	//bi-directional many-to-one association to PermisoXRol
	@OneToMany(mappedBy="funcionalidade")
	private List<PermisoXRol> permisosXRols;

	public Funcionalidad() {
	}

	public long getFuncionalidadId() {
		return this.funcionalidadId;
	}

	public void setFuncionalidadId(long funcionalidadId) {
		this.funcionalidadId = funcionalidadId;
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

	public List<PermisoXRol> getPermisosXRols() {
		return this.permisosXRols;
	}

	public void setPermisosXRols(List<PermisoXRol> permisosXRols) {
		this.permisosXRols = permisosXRols;
	}

	public PermisoXRol addPermisosXRol(PermisoXRol permisosXRol) {
		getPermisosXRols().add(permisosXRol);
		permisosXRol.setFuncionalidade(this);

		return permisosXRol;
	}

	public PermisoXRol removePermisosXRol(PermisoXRol permisosXRol) {
		getPermisosXRols().remove(permisosXRol);
		permisosXRol.setFuncionalidade(null);

		return permisosXRol;
	}

	/**
	 * @return the padre
	 */
	public Long getPadre() {
		return padre;
	}

	/**
	 * @param padre the padre to set
	 */
	public void setPadre(Long padre) {
		this.padre = padre;
	}
	
	/**
	 * @return the icono
	 */
	public String getIcono() {
		return icono;
	}

	/**
	 * @param icono the icono to set
	 */
	public void setIcono(String icono) {
		this.icono = icono;
	}
	
//	@Transient
//	public String toString() {
//		return this.descripcion;
//	}

}