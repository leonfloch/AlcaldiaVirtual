/**
 * 
 */
package com.uniandes.ecos.seguridad;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.interfaz.facade.ISeguridadFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;
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
	 * Componente de parametrizacion
	 */
	@Inject
	IParametrizacionFacade iParametrizacionFacade;
	
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
	 * Representa el id del municipio cuando el ingreso
	 * se realiza por un ciudadan a traves de la pagina de
	 * una alcaldia
	 */
	private long idMunicipio;
	
	
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
		//TODO
		//el ingreso de los ciudadanos viene a traves de la pagina de una alcaldia, por
		//lo tanto siempre debe venir el id de la alcaldia.
		//Se usa un demo para el codigo
		idMunicipio = Constantes.MUNICIPIO_ID_DEMO;
		
	}
	
	/**
	 * Realiza login del usuario en el sistema
	 */
	public String autenticar() {
		String redirect = null;
		try {
			UsuarioSesion usuario = iSeguridadFacade.autenticar(Integer.parseInt(cedula), password, funcionario);			
			this.adicionarVariableSesion(Constantes.SESION_USUARIO, usuario);								
			if (!funcionario) {
				this.cargarMunicipoEnSession();
			}			
			
			redirect = RutasApp.INICIO_RUTA;
			
		} catch (SeguridadException e) {
			adicionarMensaje(Constantes.ERROR, e.getMsg());			
		}
		return redirect;		
	}
	
	/**
	 * Carga en session el municipo de la alcaldia donde se redirecciono el login
	 */
	private void cargarMunicipoEnSession() {
		try {
			Municipio municipio = iParametrizacionFacade.obtenerMunicipio(idMunicipio);
			this.adicionarVariableSesion(Constantes.SESION_MUNICIPIO_CIUDADANO, municipio);
		} catch (NegocioException e) {
			adicionarMensaje(Constantes.ERROR, e.getMensaje());
		}
	}
	
	/**
	 * Cierra la sesion de usuario
	 * @return
	 */
	public String cerrarSesion() {
		FacesContext contextoFaces = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contextoFaces.getExternalContext();
        HttpSession sesion = (HttpSession)contextoExterno.getSession(true);        
        sesion.removeAttribute(Constantes.SESION_USUARIO);
        contextoExterno.invalidateSession();                
		return RutasApp.LOGIN_RUTA;
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


	/**
	 * @return the idMunicipio
	 */
	public long getIdMunicipio() {
		return idMunicipio;
	}


	/**
	 * @param idMunicipio the idMunicipio to set
	 */
	public void setIdMunicipio(long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}



}
