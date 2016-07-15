/**
 * 
 */
package com.uniandes.ecos.seguridad;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.dtos.UsuarioDto;
import com.uniandes.ecos.facadeInterface.ISeguridadFacade;
import com.uniandes.ecos.util.SeguridadException;

/**
 * @author leonardovalbuenacalderon
 *
 */
@ViewScoped
@ManagedBean
public class SeguridadMBean extends BaseMBean {
	
	
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Componente de Seguridad 
	 */
	@Inject
	ISeguridadFacade iSeguridadFacade;
	
	/**
	 * Cedula del usuario
	 */
	private int cedula;
	
	/**
	 * Clave del usuario
	 */
	private String password;
	
	/**
	 * Representa el usuario
	 */
	private UsuarioDto usuarioDto;
	
	
	/**
	 * 
	 */
	public SeguridadMBean() {
		
	}
	
	
	/**
	 *  
	 */
	@PostConstruct
	public void init() {
		usuarioDto = new UsuarioDto();
	}
	
	/**
	 * Realiza login del usuario en el sistema
	 */
	public String autenticar() {
		String redirect = null;
		try {
			iSeguridadFacade.autenticar(cedula, password);
			redirect = RutasApp.INICIO_RUTA;
			
		} catch (SeguridadException e) {
			adicionarMensaje('E', e.getMsg());			
		}
		
		return redirect;
		
	}
	
	/**
	 * Crea una nueva cuenta de usuario en el sistema
	 * @return
	 */
	public String crearCuenta() {
		String redirect = null;
		
		//TODO implementar logica
		try {
			iSeguridadFacade.registrarUsuario(usuarioDto);
			adicionarMensaje('I', "Usuario Creado Correctamente");
			redirect = RutasApp.LOGIN_RUTA;
			
			
		} catch (SeguridadException e) {
			adicionarMensaje('E', e.getMsg());
		}				
		return redirect;
	}

	/**
	 * @return the cedula
	 */
	public int getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the usuarioDto
	 */
	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	/**
	 * @param usuarioDto the usuarioDto to set
	 */
	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

}
