package com.uniandes.ecos.seguridad;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.facadeInterface.ITramitesFacade;
import com.uniandes.ecos.util.NegocioException;

@ViewScoped
@ManagedBean
public class ArchivoTramiteMBean extends BaseMBean {
	
	@Inject
	private ITramitesFacade tramitesFacade;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3232964861506847360L;
	private static Logger log = Logger.getLogger(ArchivoTramiteMBean.class.getName());
	
	private long tramiteId;

	public void uploadFileListener(FileUploadEvent event) {
		ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
		
		try {
			tramitesFacade.cargarArchivoTramite(tramiteId, event.getFile().getFileName(), context.getRealPath("/"), event.getFile().getInputstream());
			adicionarMensajeDefinido('I', "archivoCargadoExito");
		} catch (NegocioException | IOException e) {
			adicionarMensajeDefinido('E', "errorServidor");
			log.log(Level.SEVERE, e.getMessage());
		}

	}

}
