/**
 * 
 */
package com.uniandes.ecos.dtos;

/**
 * @author leo
 *
 */
public class UsuarioDto {
	
	private int identificacion;
	private String tipoIdentificacion;
	private String email;
	private String clave;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String telefono;
	
	
	
	/**
	 * @return the identificacion
	 */
	public int getIdentificacion() {
		return identificacion;
	}
	/**
	 * @param identificacion the identificacion to set
	 */
	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}
	/**
	 * @return the tipoIdentificacion
	 */
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	/**
	 * @param tipoIdentificacion the tipoIdentificacion to set
	 */
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}
	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	

}
