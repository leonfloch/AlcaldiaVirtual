package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Clase de persistencia para la tabla "CAMPOS_FORMULARIO". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="CAMPOS_FORMULARIO")
@NamedQuery(name="CampoFormulario.findAll", query="SELECT c FROM CampoFormulario c")
public class CampoFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CAMPOS_FORMULARIO_CAMPOID_GENERATOR", sequenceName="SEQ_CAMPO_FORMULARIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CAMPOS_FORMULARIO_CAMPOID_GENERATOR")
	@Column(name="CAMPO_ID")
	private long campoId;

	private String estado;

	private BigDecimal longitud;
	
	@Column(name="TIPO_ENTRADA")
	private String tipoEntrada;

	private String nombre;

	private String requerido;

	@Column(name="TEXTO_AYUDA")
	private String textoAyuda;

	@Column(name="TEXTO_ERROR")
	private String textoError;

	//bi-directional many-to-one association to Formulario
	@ManyToOne
	@JoinColumn(name="FORMULARIO_ID")
	private Formulario formulario;

	//bi-directional many-to-one association to ListaCampo
	@ManyToOne
	@JoinColumn(name="CODIGO_LISTA")
	private ListaCampo listasCampo;

	//bi-directional many-to-one association to TipoCampo
	@ManyToOne
	@JoinColumn(name="TIPO_CAMPO_ID")
	private TipoCampo tiposCampo;

	public CampoFormulario() {
	}

	public long getCampoId() {
		return this.campoId;
	}

	public void setCampoId(long campoId) {
		this.campoId = campoId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getLongitud() {
		return this.longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRequerido() {
		return this.requerido;
	}

	public void setRequerido(String requerido) {
		this.requerido = requerido;
	}

	public String getTextoAyuda() {
		return this.textoAyuda;
	}

	public void setTextoAyuda(String textoAyuda) {
		this.textoAyuda = textoAyuda;
	}

	public String getTextoError() {
		return this.textoError;
	}

	public void setTextoError(String textoError) {
		this.textoError = textoError;
	}

	public Formulario getFormulario() {
		return this.formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public ListaCampo getListasCampo() {
		return this.listasCampo;
	}

	public void setListasCampo(ListaCampo listasCampo) {
		this.listasCampo = listasCampo;
	}

	public TipoCampo getTiposCampo() {
		return this.tiposCampo;
	}

	public void setTiposCampo(TipoCampo tiposCampo) {
		this.tiposCampo = tiposCampo;
	}

	/**
	 * @return the tipoEntrada
	 */
	public String getTipoEntrada() {
		return tipoEntrada;
	}

	/**
	 * @param tipoEntrada the tipoEntrada to set
	 */
	public void setTipoEntrada(String tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}
	
}