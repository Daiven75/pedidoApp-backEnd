package com.lucasilva.pedidoapp.services.exceptions;

public class ClienteNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ClienteNotFoundException(String msg) {
		super(msg);
	}
	
	public ClienteNotFoundException(String msg, Throwable error) {
		super(msg, error);
	}
}
