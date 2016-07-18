package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Clase de persistencia para la tabla "OPCIONES_LISTA". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="OPCIONES_LISTA")
@NamedQuery(name="OpcionLista.findAll", query="SELECT o FROM OpcionLista o")
public class OpcionLista implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OPCIONES_LISTA_CONSECUTIVO_GENERATOR", sequenceName="SEQ_OPCION_LISTA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OPCIONES_LISTA_CONSECUTIVO_GENERATOR")
	private long consecutivo;

	private String descripcion;

	private String etiqueta;

	private BigDecimal valor;

	//bi-directional many-to-one association to ListaCampo
	@ManyToOne
	@JoinColumn(name="CODIGO_LISTA")
	private ListaCampo listasCampo;

	public OpcionLista() {
	}

	public long getConsecutivo() {
		return this.consecutivo;
	}

	public void setConsecutivo(long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEtiqueta() {
		return this.etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ListaCampo getListasCampo() {
		return this.listasCampo;
	}

	public void setListasCampo(ListaCampo listasCampo) {
		this.listasCampo = listasCampo;
	}

}