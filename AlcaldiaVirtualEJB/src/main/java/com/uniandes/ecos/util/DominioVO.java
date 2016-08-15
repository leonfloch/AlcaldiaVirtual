package com.uniandes.ecos.util;

/**
 * Generalización para listados de dominios 
 * 
 * @author Juan Albarracín
 * @version 1.0
 * @date 14/08/2016
 */
public class DominioVO {

	/** Código de la opción. */
	private String codigo;
	
	/** Valor para la opción. */
	private String valor;

	/**
	 * Constructor con parámetros. 
	 * 
	 * @param codigo
	 * @param valor
	 */
	public DominioVO(String codigo, String valor) {
		super();
		this.codigo = codigo;
		this.valor = valor;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
