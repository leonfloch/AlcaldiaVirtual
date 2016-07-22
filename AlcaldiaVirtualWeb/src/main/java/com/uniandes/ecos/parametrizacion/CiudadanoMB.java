/**
 * 
 */
package com.uniandes.ecos.parametrizacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.facadeInterface.IParametrizacionFacade;
import com.uniandes.ecos.util.NegocioException;

/**
 * Manejo de pantalla para el registro de ciudadanos
 * @author 80221940
 *
 */
@ViewScoped
@ManagedBean
public class CiudadanoMB {
	
	/** Inyección de dependecia con fachada de parametrización. */
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
		
		//TODO terminar implementacion
				
		try {
			iParametrizacionFacade.registrarCiudadano(ciudadano);
		} catch (NegocioException e) {
			
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
