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
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Mbean encargado de la parametrizacion de funcionarios
 * @author 80221940
 *
 */
@ViewScoped
@ManagedBean
public class FuncionariosMB extends BaseMBean {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * servicio de parametrizacion
	 */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	
	/**
	 * Listado de usuarios del sistema
	 */
	private List<UsuariosFuncionario> usuarios;
	
	/**
	 * Usuario seleccionado para edicion
	 */
	private UsuariosFuncionario usuarioSelecc;
	
	/**
	 * lista de roles creados en el sistema
	 */
	private List<Rol> roles;
	
	/**
	 * listado de municipios en el sistema
	 */
	private List<Municipio> municipios;
	
	/**
	 * indica si se cra un nuevo funcionario
	 */
	private boolean creacion;	
	
	/**
	 * indica si se muestra la lista de municipios
	 */
	private boolean muestraMunicipio;
	
	
	
	
	/**
	 * Constructor
	 */
	public FuncionariosMB () {
		creacion = false;
		muestraMunicipio = false;
		this.initFuncionario();

	}
	
	/**
	 * limpia la informacion del funcionario
	 */
	private void initFuncionario() {
		usuarioSelecc = new UsuariosFuncionario();
		usuarioSelecc.setRole(new Rol());
		usuarioSelecc.setMunicipio(new Municipio());
		usuarioSelecc.setPersona(new Persona());
	}
	
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {			
			usuarios = iParametrizacionFacade.obtenerFuncionarios(this.getSesion().getMunicipioId());
			roles = iParametrizacionFacade.obtenerRoles();
			municipios = new ArrayList<Municipio>();
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
		} else {
			this.validaMunicipios();
		}
	}
	
	
	/**
	 * Persiste el usuario seleccionado
	 */
	public void persistirUsuario() {
		try {
			usuarioSelecc.setUsuario(String.valueOf(usuarioSelecc.getPersona().getNumIdentificacion()));			
							
			if (creacion) {
				iParametrizacionFacade.registrarFuncionario(usuarioSelecc);	
				usuarios = iParametrizacionFacade.obtenerFuncionarios(this.getSesion().getMunicipioId());
			} else {
				iParametrizacionFacade.actualizarFuncionario(usuarioSelecc);
			}				
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
		creacion = false;
	}
	
	
	
	/**
	 * Valida si se deben cargar o no municipios
	 */
	public void validaMunicipios() {
		municipios = new ArrayList<Municipio>();
		
		if (usuarioSelecc.getRole() != null) {
			if (Constantes.ROL_ADMIN_MINTIC != usuarioSelecc.getRole().getRolId() && 
					Constantes.ROL_CIUDADANO != usuarioSelecc.getRole().getRolId()) {
				try {
					municipios = iParametrizacionFacade.obtenerMunicipios();
				} catch (NegocioException e) {
				}				
			}
		}		
	}
	

	/**
	 * @return the usuarios
	 */
	public List<UsuariosFuncionario> getUsuarios() {
		return usuarios;
	}


	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(List<UsuariosFuncionario> usuarios) {
		this.usuarios = usuarios;
	}


	/**
	 * @return the usuarioSelecc
	 */
	public UsuariosFuncionario getUsuarioSelecc() {
		return usuarioSelecc;
	}


	/**
	 * @param usuarioSelecc the usuarioSelecc to set
	 */
	public void setUsuarioSelecc(UsuariosFuncionario usuarioSelecc) {
		this.usuarioSelecc = usuarioSelecc;
	}


	/**
	 * @return the roles
	 */
	public List<Rol> getRoles() {
		return roles;
	}


	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
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


	/**
	 * @return the municipios
	 */
	public List<Municipio> getMunicipios() {
		return municipios;
	}


	/**
	 * @param municipios the municipios to set
	 */
	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}

	/**
	 * @return the muestraMunicipio
	 */
	public boolean isMuestraMunicipio() {
		return muestraMunicipio;
	}

	/**
	 * @param muestraMunicipio the muestraMunicipio to set
	 */
	public void setMuestraMunicipio(boolean muestraMunicipio) {
		this.muestraMunicipio = muestraMunicipio;
	}

}
