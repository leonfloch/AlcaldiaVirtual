/**
 * 
 */
package com.uniandes.ecos.tramite;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.util.Constantes;

/**
 * mbean encargado de realizar el proceso de tramites para los 
 * ciudadanos
 * @author Leonardo Valbuena 
 *
 */
@SessionScoped
@ManagedBean
public class TramiteMB extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista de tipos de tramites que puede realizar el ciudadano
	 */
	private List<TipoTramite> tiposTramites;
	
	/**
	 * Municipio de la alcaldia donde se redirecciono al ciudadano
	 */
	private Municipio alcaldiaMunicipio;
	
	/**
	 * Constructor
	 */
	public TramiteMB() {
		tiposTramites = new ArrayList<TipoTramite>();
		alcaldiaMunicipio = (Municipio)obtenerVariableSesion(Constantes.SESION_MUNICIPIO_CIUDADANO);
	}
	
	/**
	 * 
	 */
	@PostConstruct
	public void init() {		
		this.cargarTiposTramites();
		
	}
	
	/**
	 * Inicia un tramite con el tipo de tramite
	 * seleccionado
	 * @return
	 */
	public String realizarTramite() {
		//TODO realizar implementacion
		return null;
	}
	
	/**
	 * Se cargan los tipos de tramites para el municipio en sesion
	 */
	private void cargarTiposTramites() {

		for (TramiteXMunicipio tramiteXMunicipio : alcaldiaMunicipio.getTramitesXMunicipios()) {
			tiposTramites.add(tramiteXMunicipio.getTiposTramite());
		}
	}


	/**
	 * @return the tiposTramites
	 */
	public List<TipoTramite> getTiposTramites() {
		return tiposTramites;
	}


	/**
	 * @param tiposTramites the tiposTramites to set
	 */
	public void setTiposTramites(List<TipoTramite> tiposTramites) {
		this.tiposTramites = tiposTramites;
	}

	/**
	 * @return the alcaldiaMunicipio
	 */
	public Municipio getAlcaldiaMunicipio() {
		return alcaldiaMunicipio;
	}

	/**
	 * @param alcaldiaMunicipio the alcaldiaMunicipio to set
	 */
	public void setAlcaldiaMunicipio(Municipio alcaldiaMunicipio) {
		this.alcaldiaMunicipio = alcaldiaMunicipio;
	}

}
