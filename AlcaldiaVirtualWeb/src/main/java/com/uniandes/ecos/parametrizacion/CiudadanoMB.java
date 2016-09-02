/**
 * 
 */
package com.uniandes.ecos.parametrizacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.comun.RutasApp;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.util.NegocioException;

/**
 * Manejo de pantalla para el registro de ciudadanos
 * @author 80221940
 *
 */
@ViewScoped
@ManagedBean
public class CiudadanoMB extends BaseMBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Inyecci�n de dependecia con fachada de parametrizaci�n. */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	
	/** representa el ciudadano que se va a crear **/
	private UsuariosCiudadano ciudadano;
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		ciudadano = new UsuariosCiudadano();
		ciudadano.setPersona(new Persona());
		
	}
	
	/**
	 * Se crea un nuevo ciudadano en el sistema
	 * @return
	 */
	public String crearCiudadano() {
		String redirect = "";				
		try {
			iParametrizacionFacade.registrarCiudadano(ciudadano);
			redirect = RutasApp.LOGIN_RUTA;
			
		} catch (NegocioException e) {
			adicionarMensaje(e.getTipo(), e.getMensaje());
		}
		return redirect;
	}		


	/**
	 * @return the ciudadano
	 */
	public UsuariosCiudadano getCiudadano() {
		return ciudadano;
	}


	/**
	 * @param ciudadano the ciudadano to set
	 */
	public void setCiudadano(UsuariosCiudadano ciudadano) {
		this.ciudadano = ciudadano;
	}
	
	

}
