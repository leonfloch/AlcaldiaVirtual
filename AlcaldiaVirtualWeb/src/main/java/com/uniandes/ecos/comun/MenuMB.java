/**
 * 
 */
package com.uniandes.ecos.comun;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.uniandes.ecos.entities.Funcionalidad;
import com.uniandes.ecos.entities.PermisoXRol;
import com.uniandes.ecos.entities.UsuarioSesion;

/**
 * @author 80221940
 *
 */
@SessionScoped
@ManagedBean
public class MenuMB extends BaseMBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * representa el usuario de sesion
	 */
	private UsuarioSesion usuario;
	
	
	
	

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		usuario = (UsuarioSesion)obtenerVariableSesion(ConstantesApp.SESION_USUARIO);
	}
	
	/**
	 * Retorna las opciones de menu que tiene acceso el usuario
	 * @return
	 */
	public List<Funcionalidad> getMenuUsuario() {
		List<Funcionalidad> menus = new ArrayList<Funcionalidad>();
		for (PermisoXRol permisosXrol : usuario.getRole().getPermisosXRols()) {
			menus.add(permisosXrol.getFuncionalidade());			
		}
		return menus;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Funcionalidad> getMenuPadre() {
		List<Funcionalidad> menus = new ArrayList<Funcionalidad>();
		for (PermisoXRol permisosXrol : usuario.getRole().getPermisosXRols()) {
			if (permisosXrol.getFuncionalidade().getPadre() == null) {
				menus.add(permisosXrol.getFuncionalidade());
			}						
		}
		return menus;
	}
	
	/**
	 * 
	 * @param padre
	 * @return
	 */
	public List<Funcionalidad> menuHijos(final long padreId) {
		List<Funcionalidad> menus = new ArrayList<Funcionalidad>();
		for (PermisoXRol permisosXrol : usuario.getRole().getPermisosXRols()) {
			if (permisosXrol.getFuncionalidade().getPadre() != null && 
					permisosXrol.getFuncionalidade().getPadre() == padreId) {
				menus.add(permisosXrol.getFuncionalidade());
			}
		}		
		return menus;
	}
	
	
	
	/**
	 * @return the usuario
	 */
	public UsuarioSesion getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioSesion usuario) {
		this.usuario = usuario;
	}

	

}
