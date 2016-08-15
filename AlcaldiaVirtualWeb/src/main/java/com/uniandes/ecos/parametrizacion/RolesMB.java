/**
 * 
 */
package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.util.NegocioException;

/**
 * @author leonardovalbuenacalderon
 *
 */
@ViewScoped
@ManagedBean
public class RolesMB extends BaseMBean {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	
	/**
	 * lista de roles creados en el sistema
	 */
	private List<Rol> roles;
	
	/**
	 * representa un rol seleccionado
	 */
	private Rol rolSeleccionado;
	
	
	/**
	 * constructor
	 */
	public RolesMB() {
		roles = new ArrayList<Rol>();
	}
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			rolSeleccionado = new Rol();
			roles = iParametrizacionFacade.obtenerRoles();
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	/**
	 * Actualiza el rol seleccionado
	 */
	public void actualizarRol() {
		try {
			iParametrizacionFacade.actualizarRol(rolSeleccionado);
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	/**
	 * Crea un nuevo rol en el sistem
	 */
	public void crearRol() {
		try {
			iParametrizacionFacade.crearRol(rolSeleccionado);
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	
	
	

	/**
	 * @return the roles
	 */
	public List<Rol> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	/**
	 * @return the rolSeleccionado
	 */
	public Rol getRolSeleccionado() {
		return rolSeleccionado;
	}

	/**
	 * @param rolSeleccionado the rolSeleccionado to set
	 */
	public void setRolSeleccionado(Rol rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

}
