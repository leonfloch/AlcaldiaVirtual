package com.uniandes.ecos.util;

/**
 * Generalizaci�n para listados de dominios 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 14/08/2016
 */
public class DominioVO {

	/** C�digo de la opci�n. */
	private String codigo;
	
	/** Valor para la opci�n. */
	private String valor;

	/**
	 * Constructor con par�metros. 
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
