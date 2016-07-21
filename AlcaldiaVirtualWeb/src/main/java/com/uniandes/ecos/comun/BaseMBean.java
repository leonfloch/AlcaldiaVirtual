package com.uniandes.ecos.comun;



import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Clase genérica los comportamientos comunes de los Mangedbean de la aplicación.
 *
 */
public class BaseMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ResourceBundle mensajes;
	
	public BaseMBean()
	{
		mensajes = ResourceBundle.getBundle("resources.messages");
	}
	
	public void adicionarMensajeDefinido(char tipoMensaje, String key){
		String mensaje = mensajes.getString(key);
		adicionarMensaje(tipoMensaje, mensaje);
	}

	/**
	 * Muestar el mensaje en pantalla.
	 * 
	 * @param tipoMensaje: E(error), W(advertencia) y I(información)
	 * @param mensaje
	 */
	public void adicionarMensaje(char tipoMensaje, String mensaje){
		switch (tipoMensaje) {
		case 'E':
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Error!", mensaje));
			break;
		case 'W':
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Advertencia!", mensaje));
			break;
		case 'I':
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
			break;
		default:
			break;
		}
	}
	/**
	 * Adiciona un objeto de sesion a la sesion WEB del usuario
	 * @param llave
	 * @param objeto
	 */
	public void adicionarVariableSesion(String llave, Object objeto)
	{
		//Obtiene la sesión WEB
		FacesContext contextoFaces = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contextoFaces.getExternalContext();
        HttpSession sesion = (HttpSession)contextoExterno.getSession(true);
        //Valida que la sesion no sea nula
        if(sesion != null)
        {
        	//Adiciona el mensaje web
        	sesion.setAttribute(llave, objeto);
        }
	}
	
	/**
	 * Recupera un objeto de la sesión WEB del usuario
	 * @param llave
	 * @return
	 */
	public Object obtenerVariableSesion(String llave)
	{
		//Obtiene la sesión WEB
		FacesContext contextoFaces = FacesContext.getCurrentInstance();
        ExternalContext contextoExterno = contextoFaces.getExternalContext();
        HttpSession sesion = (HttpSession)contextoExterno.getSession(true);
        //valida que la sesion no sea null
        if(sesion!=null)
        {
        	//Retorna el objeto
        	return sesion.getAttribute(llave);
        }
        else
        {
        	//Si la sesión es null, se retorna null, se debe tener cuidado con la validación
        	//del valor de retorno
        	return null;
        }      
	}

}
