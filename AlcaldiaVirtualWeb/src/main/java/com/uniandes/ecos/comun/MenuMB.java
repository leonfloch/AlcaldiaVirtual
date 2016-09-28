/**
 * 
 */
package com.uniandes.ecos.comun;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import com.uniandes.ecos.entities.Funcionalidad;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.PermisoXRol;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.util.Constantes;

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
	 * Indica el municipio de la sesion
	 */
	private Municipio municipio;
	
	
	
	

	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		usuario = (UsuarioSesion)obtenerVariableSesion(Constantes.SESION_USUARIO);
		cargarMunicipio();
	}
	
	/**
	 * Carga el municipio pada el menu
	 */
	private void cargarMunicipio() {
		if (usuario.getMunicipio() != null) {
			municipio = usuario.getMunicipio(); 
		} else {
			municipio = (Municipio)obtenerVariableSesion(Constantes.SESION_MUNICIPIO_CIUDADANO);
		}
			
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
