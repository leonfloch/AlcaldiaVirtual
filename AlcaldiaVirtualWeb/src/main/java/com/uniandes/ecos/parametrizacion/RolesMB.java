/**
 * 
 */
package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DualListModel;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Funcionalidad;
import com.uniandes.ecos.entities.PermisoXRol;
import com.uniandes.ecos.entities.Rol;
import com.uniandes.ecos.interfaz.facade.IParametrizacionFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

/**
 * Calse encargada del manejo de la parametrizacion de roles
 * @author leonardovalbuenacalderon
 *
 */
@ViewScoped
@ManagedBean
public class RolesMB extends BaseMBean {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
	private IParametrizacionFacade iParametrizacionFacade;
	
	/**
	 * lista de roles creados en el sistema
	 */
	private List<Rol> roles;
	
	/**
	 * representa un rol seleccionado
	 */
	private Rol rolSeleccionado;
	
	
	/**
	 * lista de funcionalidades del sistema
	 */
	private DualListModel<Funcionalidad> funcionalidades;
	private List<Funcionalidad> funcsSource;
	private List<Funcionalidad> funcsTarget;
	
	
	
	/**
	 * constructor
	 */
	public RolesMB() {
		roles = new ArrayList<Rol>();
	}
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {
		try {
			funcsSource = new ArrayList<Funcionalidad>();
			funcsTarget = new ArrayList<Funcionalidad>();
			funcionalidades = new DualListModel<Funcionalidad>();
			
			rolSeleccionado = new Rol();
			roles = iParametrizacionFacade.obtenerRoles();
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	/**
	 * Realiza la configuracion de la seleccion de funcionalidades
	 * @throws NegocioException 
	 */
	public void preEdicion() {		
		try {
			funcsSource = iParametrizacionFacade.obtenerFuncionalidades();
			funcsTarget = new ArrayList<Funcionalidad>();
			funcsTarget.addAll(funcsSource);
			
			for (PermisoXRol permisoXRol : rolSeleccionado.getPermisosXRols()) {
				
				//TODO terminar implementacion
				

								
				Iterator<Funcionalidad> it = funcsSource.iterator();
				while(it.hasNext()) {
					if (permisoXRol.getFuncionalidade().getFuncionalidadId() == it.next().getFuncionalidadId()) {
						it.remove();
						break;
					}
				}
				
				Iterator<Funcionalidad> itT =funcsTarget.iterator();
				while(itT.hasNext()) {
					
					
					
					if (permisoXRol.getFuncionalidade().getFuncionalidadId() != it.next().getFuncionalidadId()) {
						itT.remove();
						break;
					}
				}
				
			}
			funcionalidades = new DualListModel<Funcionalidad>(funcsSource, funcsTarget);
			
			
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	
	
	
	/**
	 * Actualiza el rol seleccionado
	 */
	public void actualizarRol() {
		try {
			//actualiza los permisos seleccionads
			rolSeleccionado.getPermisosXRols().clear();
			for (Funcionalidad funcionalidad : funcsTarget) {
				PermisoXRol permisoXrol = new PermisoXRol();
				permisoXrol.setEstado(Constantes.ACTIVO);
				permisoXrol.setFuncionalidade(funcionalidad);
				permisoXrol.setRole(rolSeleccionado);
				rolSeleccionado.addPermisosXRol(permisoXrol);
			}
			
			iParametrizacionFacade.actualizarRol(rolSeleccionado);
			
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	/**
	 * Crea un nuevo rol en el sistem
	 */
	public void crearRol() {
		try {
			iParametrizacionFacade.crearRol(rolSeleccionado);
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
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
	 * @return the rolSeleccionado
	 */
	public Rol getRolSeleccionado() {
		return rolSeleccionado;
	}

	/**
	 * @param rolSeleccionado the rolSeleccionado to set
	 */
	public void setRolSeleccionado(Rol rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	/**
	 * @return the funcionalidades
	 */
	public DualListModel<Funcionalidad> getFuncionalidades() {
		return funcionalidades;
	}

	/**
	 * @param funcionalidades the funcionalidades to set
	 */
	public void setFuncionalidades(DualListModel<Funcionalidad> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

}
