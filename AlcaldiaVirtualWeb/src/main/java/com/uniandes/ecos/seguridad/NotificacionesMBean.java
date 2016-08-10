package com.uniandes.ecos.seguridad;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.dtos.CorreoElectronicoDto;
import com.uniandes.ecos.interfaz.facade.IProcesadorTramitesFacade;
import com.uniandes.ecos.util.NegocioException;

@ViewScoped
@ManagedBean
public class NotificacionesMBean extends BaseMBean {

	@Inject
	private IProcesadorTramitesFacade tramitesFacade;
	
	private static Logger log = Logger.getLogger(NotificacionesMBean.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = -4931422252125509015L;
	private String de;
	private String para;
	private String asunto;
	private String contenido;
	private String contrasenia;
	
	public void enviarCorreo(){
		
		CorreoElectronicoDto correo = new CorreoElectronicoDto();
		correo.setUsuario(de);
		correo.setContrasenia(contrasenia);
		correo.setPara(para);
		correo.setAsunto(asunto);
		correo.setContenido(contenido);
		
		correo.setUsuario("ecos.alcaldia.virtual@gmail.com");
		correo.setContrasenia("ecos@alcaldia@virtual");
		
		try {
			tramitesFacade.enviarCorreo(correo);
			adicionarMensajeDefinido('I', "emailEnviadoExito");
		} catch (NegocioException e) {
			adicionarMensajeDefinido('E', "errorServidor");
			log.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public String getDe() {
		return de;
	}
	public void setDe(String de) {
		this.de = de;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	

}
