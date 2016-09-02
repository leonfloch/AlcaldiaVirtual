/**
 * 
 */
package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.Persona;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.entities.UsuariosCiudadano;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Mbean encargado de la parametrizacion de ciudadanos
 * @author 80221940
 *
 */
@ViewScoped
@ManagedBean
public class AdminCiudadanoMB extends BaseMBean {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * servicio de parametrizacion
	 */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	
	/**
	 * Listado de usuarios del sistema
	 */
	private List<UsuariosCiudadano> usuarios;
	
	/**
	 * Usuario seleccionado para edicion
	 */
	private UsuariosCiudadano usuarioSelecc;	
	
	/**
	 * indica si se cra un nuevo funcionario
	 */
	private boolean creacion;	
	

	
	/**
	 * Constructor
	 */
	public AdminCiudadanoMB () {
		creacion = false;
		this.initFuncionario();

	}
	
	/**
	 * limpia la informacion del funcionario
	 */
	private void initFuncionario() {
		usuarioSelecc = new UsuariosCiudadano();
		usuarioSelecc.setPersona(new Persona());
	}
	
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {			
			usuarios = iParametrizacionFacade.obtenerCiudadanos();
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}		
	}
	
	/**
	 * Se ejecuta antes de abril el modal de edicion de usuarios
	 */
	public void preModal() {		
		if (creacion) {
			this.initFuncionario();
		}
	}
	
	
	/**
	 * Persiste el usuario seleccionado
	 */
	public void persistirUsuario() {
		try {
			usuarioSelecc.setUsuario(String.valueOf(usuarioSelecc.getPersona().getNumIdentificacion()));
			if (creacion) {
				iParametrizacionFacade.registrarCiudadano(usuarioSelecc);	
				usuarios = iParametrizacionFacade.obtenerCiudadanos();
			} else {
				iParametrizacionFacade.actualizarCiudadano(usuarioSelecc);
			}				
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
		creacion = false;
	}
	

	/**
	 * @return the usuarios
	 */
	public List<UsuariosCiudadano> getUsuarios() {
		return usuarios;
	}


	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<UsuariosCiudadano> usuarios) {
		this.usuarios = usuarios;
	}


	/**
	 * @return the usuarioSelecc
	 */
	public UsuariosCiudadano getUsuarioSelecc() {
		return usuarioSelecc;
	}


	/**
	 * @param usuarioSelecc the usuarioSelecc to set
	 */
	public void setUsuarioSelecc(UsuariosCiudadano usuarioSelecc) {
		this.usuarioSelecc = usuarioSelecc;
	}


	/**
	 * @return the creacion
	 */
	public boolean isCreacion() {
		return creacion;
	}


	/**
	 * @param creacion the creacion to set
	 */
	public void setCreacion(boolean creacion) {		
		this.creacion = creacion;
	}


}
