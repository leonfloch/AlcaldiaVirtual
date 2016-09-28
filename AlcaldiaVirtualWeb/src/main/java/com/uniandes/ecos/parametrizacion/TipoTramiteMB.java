package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DualListModel;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

@ViewScoped
@ManagedBean
public class TipoTramiteMB extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698695809011793289L;

	/**
	 * Servicio de parametrizacion
	 */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;

	/**
	 * Lista de tipos tramite
	 */
	private List<TipoTramite> tiposTramite;
	/**
	 * Lista Municipios disponibles
	 */
	private List<Municipio> municipiosDisponibles;
	/**
	 * Lista Municipios seleccionados
	 */
	private List<Municipio> municipiosSeleccionados;
	/**
	 * Lista de municipios
	 */
	private DualListModel<Municipio> municipios;

	/**
	 * Tipo de tramite seleccionado para edicion
	 */
	private TipoTramite tipoTramiteSelecc;

	/**
	 * indica si se cra un nuevo funcionario
	 */
	private boolean creacion;

	/**
	 * Constructor
	 */
	public TipoTramiteMB() {
		creacion = false;
		this.initTiposTramite();
	}

	@PostConstruct
	public void init() {
		this.consultarTiposTramite();
		
		try {
			municipiosDisponibles = iParamTramitesFacade.obtenerListaMunicipios();
			municipios = new DualListModel<>(municipiosDisponibles, new ArrayList<Municipio>());
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Se ejecuta antes de abril el modal de edicion de tipos tramite
	 */
	public void preModal() {
				
		if (creacion) {
			this.initTiposTramite();
		}else{
			try {
				municipiosSeleccionados = iParamTramitesFacade.obtenerListaMunicipiosXTipoTramite(tipoTramiteSelecc.getTipoTramiteId());
				List<Municipio> municipiosEliminar = new ArrayList<>();
				
				for(Municipio md : municipiosDisponibles){
					for(Municipio ms : municipiosSeleccionados){
						if(md.getMunicipioId() == ms.getMunicipioId()){
							municipiosEliminar.add(md);
							break;
						}
					}
				}
				
				municipiosDisponibles.removeAll(municipiosEliminar);
				
				municipios.setTarget(municipiosSeleccionados);
			} catch (NegocioException e) {
				this.adicionarMensaje(e.getTipo(), e.getMensaje());
			}
		}
	}

	public void consultarTiposTramite() {
		try {
			tiposTramite = iParamTramitesFacade.obtenerListaTiposTramites(tipoTramiteSelecc.getNombre());
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	public void persistirTipoTramite() {
		try {
			UsuarioSesion usuario = (UsuarioSesion)this.obtenerVariableSesion(Constantes.SESION_USUARIO);
			UsuariosFuncionario usuarioFuncionario = new UsuariosFuncionario();
			usuarioFuncionario.setUsuario(usuario.getUsuario());
			
			List<Municipio> municipiosDisponibles = municipios.getSource();
			List<Municipio> municipiosSeleccionados = municipios.getTarget();
			List<TramiteXMunicipio> municipiosXTramite = new ArrayList<>();
			
			for(Municipio m : municipiosSeleccionados){
				boolean existe = false;
				TramiteXMunicipio txm = new TramiteXMunicipio();
				txm.setMunicipio(m);
				txm.setTiposTramite(tipoTramiteSelecc);
				txm.setEstado("A");
				txm.setUsuariosFuncionario(usuarioFuncionario);
				
				for(Municipio ms : this.municipiosSeleccionados){
					if(ms.getMunicipioId() == m.getMunicipioId()){
						existe = true;
						break;
					}
				}
				
				if(!existe)
					municipiosXTramite.add(txm);
			}
			
			for(Municipio m1 : this.municipiosSeleccionados){
				boolean desactivar = false;
				for(Municipio m2 : municipiosDisponibles){
					if(m1.getMunicipioId() == m2.getMunicipioId()){
						desactivar = true;
						break;
					}
				}
				
				if(desactivar){
					TramiteXMunicipio txm = new TramiteXMunicipio();
					txm.setMunicipio(m1);
					txm.setTiposTramite(tipoTramiteSelecc);
					iParamTramitesFacade.desactivarTramiteXMunicipio(txm);
				}
			}
			
			tipoTramiteSelecc.setTramitesXMunicipios(municipiosXTramite);
			iParamTramitesFacade.actualizarTipoTramite(tipoTramiteSelecc);
			
			consultarTiposTramite();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Limpia la informacion del tipo tramite
	 */
	private void initTiposTramite() {
		tipoTramiteSelecc = new TipoTramite();

	}

	/**
	 * @return the tiposTramite
	 */
	public List<TipoTramite> getTiposTramite() {
		return tiposTramite;
	}

	/**
	 * @param tiposTramite
	 *            the tiposTramite to set
	 */
	public void setTiposTramite(List<TipoTramite> tiposTramite) {
		this.tiposTramite = tiposTramite;
	}

	/**
	 * @return the tipoTramiteSelecc
	 */
	public TipoTramite getTipoTramiteSelecc() {
		return tipoTramiteSelecc;
	}

	/**
	 * @param tipoTramiteSelecc
	 *            the tipoTramiteSelecc to set
	 */
	public void setTipoTramiteSelecc(TipoTramite tipoTramiteSelecc) {
		this.tipoTramiteSelecc = tipoTramiteSelecc;
	}

	/**
	 * @return the municipios
	 */
	public DualListModel<Municipio> getMunicipios() {
		return municipios;
	}

	/**
	 * @param municipios
	 *            the municipios to set
	 */
	public void setMunicipios(DualListModel<Municipio> municipios) {
		this.municipios = municipios;
	}

	/**
	 * @return the creacion
	 */
	public boolean isCreacion() {
		return creacion;
	}

	/**
	 * @param creacion
	 *            the creacion to set
	 */
	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
	}

}
