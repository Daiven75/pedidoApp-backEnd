package com.lucasilva.pedidoapp.services.exceptions;

public class CategoriaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CategoriaNotFoundException(String msg) {
		super(msg);
	}
	
	public CategoriaNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
