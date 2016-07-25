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
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.facadeInterface.ISeguridadFacade;
import com.uniandes.ecos.util.Constantes;
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
	private String cedula;
	
	/**
	 * Clave del usuario
	 */
	private String password;	
	
	/**
	 * indica si el usuario que se logea es funcionario
	 */
	private boolean funcionario;
	
	
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
		
		
	}
	
	/**
	 * Realiza login del usuario en el sistema
	 */
	public String autenticar() {
		String redirect = null;
		try {
			UsuarioSesion usuario = iSeguridadFacade.autenticar(Integer.parseInt(cedula), password, funcionario);
			this.adicionarVariableSesion("usuario", usuario);
			redirect = RutasApp.INICIO_RUTA;
			
		} catch (SeguridadException e) {
			adicionarMensaje(Constantes.ERROR, e.getMsg());			
		}
		
		return redirect;
		
	}
	
	

	/**
	 * @return the cedula
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * @param cedula the cedula to set
	 */
	public void setCedula(String cedula) {
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
	 * @return the funcionario
	 */
	public boolean isFuncionario() {
		return funcionario;
	}


	/**
	 * @param funcionario the funcionario to set
	 */
	public void setFuncionario(boolean funcionario) {
		this.funcionario = funcionario;
	}



}
