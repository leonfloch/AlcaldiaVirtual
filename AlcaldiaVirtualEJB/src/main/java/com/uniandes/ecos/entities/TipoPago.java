package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * Clase de persistencia para la tabla "TIPOS_PAGO". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="TIPOS_PAGO")
@NamedQuery(name="TipoPago.findAll", query="SELECT t FROM TipoPago t")
public class TipoPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPOS_PAGO_TIPOPAGOID_GENERATOR", sequenceName="SEQ_TIPO_PAGO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPOS_PAGO_TIPOPAGOID_GENERATOR")
	@Column(name="TIPO_PAGO_ID")
	private long tipoPagoId;

	private String nombre;

	//bi-directional many-to-one association to Pago
	@OneToMany(mappedBy="tiposPago")
	private List<Pago> pagos;

	public TipoPago() {
	}

	public long getTipoPagoId() {
		return this.tipoPagoId;
	}

	public void setTipoPagoId(long tipoPagoId) {
		this.tipoPagoId = tipoPagoId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Pago> getPagos() {
		return this.pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public Pago addPago(Pago pago) {
		getPagos().add(pago);
		pago.setTiposPago(this);

		return pago;
	}

	public Pago removePago(Pago pago) {
		getPagos().remove(pago);
		pago.setTiposPago(null);

		return pago;
	}

}