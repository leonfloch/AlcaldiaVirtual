package com.uniandes.ecos.util;

public class NegocioException extends Exception {

	/** Serial de la clase */
	private static final long serialVersionUID = 1L;
	
	/** Indica el tipo de la excepci�n. (Error 'E', Advertencia 'W'). */
	private char tipo;
	
	/** C�digo de la excepci�n. */
	private int codigo;
	
	/** Mensaje Asociado */
	private String mensaje;
	
	/**
	 * Constructor con par�metros de la clase.
	 * 
	 * @param tipo
	 * @param codigo
	 * @param mensaje
	 */
	public NegocioException(char tipo, int codigo, String mensaje) {
		super();
		this.tipo = tipo;
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	/**
	 * @return the tipo
	 */
	public char getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
