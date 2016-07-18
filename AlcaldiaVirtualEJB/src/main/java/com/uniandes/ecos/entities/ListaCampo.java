package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * Clase de persistencia para la tabla "LISTAS_CAMPOS". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="LISTAS_CAMPOS")
@NamedQuery(name="ListaCampo.findAll", query="SELECT l FROM ListaCampo l")
public class ListaCampo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CODIGO_LISTA")
	private String codigoLista;

	private String nombre;

	//bi-directional many-to-one association to CampoFormulario
	@OneToMany(mappedBy="listasCampo")
	private List<CampoFormulario> camposFormularios;

	//bi-directional many-to-one association to OpcionLista
	@OneToMany(mappedBy="listasCampo")
	private List<OpcionLista> opcionesListas;

	public ListaCampo() {
	}

	public String getCodigoLista() {
		return this.codigoLista;
	}

	public void setCodigoLista(String codigoLista) {
		this.codigoLista = codigoLista;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<CampoFormulario> getCamposFormularios() {
		return this.camposFormularios;
	}

	public void setCamposFormularios(List<CampoFormulario> camposFormularios) {
		this.camposFormularios = camposFormularios;
	}

	public CampoFormulario addCamposFormulario(CampoFormulario camposFormulario) {
		getCamposFormularios().add(camposFormulario);
		camposFormulario.setListasCampo(this);

		return camposFormulario;
	}

	public CampoFormulario removeCamposFormulario(CampoFormulario camposFormulario) {
		getCamposFormularios().remove(camposFormulario);
		camposFormulario.setListasCampo(null);

		return camposFormulario;
	}

	public List<OpcionLista> getOpcionesListas() {
		return this.opcionesListas;
	}

	public void setOpcionesListas(List<OpcionLista> opcionesListas) {
		this.opcionesListas = opcionesListas;
	}

	public OpcionLista addOpcionesLista(OpcionLista opcionesLista) {
		getOpcionesListas().add(opcionesLista);
		opcionesLista.setListasCampo(this);

		return opcionesLista;
	}

	public OpcionLista removeOpcionesLista(OpcionLista opcionesLista) {
		getOpcionesListas().remove(opcionesLista);
		opcionesLista.setListasCampo(null);

		return opcionesLista;
	}

}