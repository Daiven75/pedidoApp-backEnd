package com.lucasilva.pedidoapp.services.exceptions;

public class EstadoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EstadoNotFoundException(String msg) {
		super(msg);
	}
	
	public EstadoNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}