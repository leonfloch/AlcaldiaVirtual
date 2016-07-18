package com.uniandes.ecos.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * Clase de persistencia para la tabla "PAGOS". 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 18/07/2016
 */
@Entity
@Table(name="PAGOS")
@NamedQuery(name="Pago.findAll", query="SELECT p FROM Pago p")
public class Pago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PAGOS_CONSECUTIVO_GENERATOR", sequenceName="SEQ_PAGO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAGOS_CONSECUTIVO_GENERATOR")
	private long consecutivo;

	@Column(name="COMPROBANTE_PAGO")
	private String comprobantePago;

	@Column(name="ENTIDAD_RECAUDADORA")
	private String entidadRecaudadora;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_PAGO")
	private Date fechaPago;

	//bi-directional many-to-one association to TipoPago
	@ManyToOne
	@JoinColumn(name="TIPO_PAGO_ID")
	private TipoPago tiposPago;

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="TRAMITE_ID")
	private Tramite tramite;

	public Pago() {
	}

	public long getConsecutivo() {
		return this.consecutivo;
	}

	public void setConsecutivo(long consecutivo) {
		this.consecutivo = consecutivo;
	}

	public String getComprobantePago() {
		return this.comprobantePago;
	}

	public void setComprobantePago(String comprobantePago) {
		this.comprobantePago = comprobantePago;
	}

	public String getEntidadRecaudadora() {
		return this.entidadRecaudadora;
	}

	public void setEntidadRecaudadora(String entidadRecaudadora) {
		this.entidadRecaudadora = entidadRecaudadora;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public TipoPago getTiposPago() {
		return this.tiposPago;
	}

	public void setTiposPago(TipoPago tiposPago) {
		this.tiposPago = tiposPago;
	}

	public Tramite getTramite() {
		return this.tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

}