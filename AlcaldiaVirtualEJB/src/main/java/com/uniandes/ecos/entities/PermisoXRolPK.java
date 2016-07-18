package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Primary Key de la tabla "PERMISOS_X_ROL". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Embeddable
public class PermisoXRolPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ROL_ID", insertable=false, updatable=false)
	private long rolId;

	@Column(name="FUNCIONALIDAD_ID", insertable=false, updatable=false)
	private long funcionalidadId;

	public PermisoXRolPK() {
	}
	public long getRolId() {
		return this.rolId;
	}
	public void setRolId(long rolId) {
		this.rolId = rolId;
	}
	public long getFuncionalidadId() {
		return this.funcionalidadId;
	}
	public void setFuncionalidadId(long funcionalidadId) {
		this.funcionalidadId = funcionalidadId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PermisoXRolPK)) {
			return false;
		}
		PermisoXRolPK castOther = (PermisoXRolPK)other;
		return 
			(this.rolId == castOther.rolId)
			&& (this.funcionalidadId == castOther.funcionalidadId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.rolId ^ (this.rolId >>> 32)));
		hash = hash * prime + ((int) (this.funcionalidadId ^ (this.funcionalidadId >>> 32)));
		
		return hash;
	}
}