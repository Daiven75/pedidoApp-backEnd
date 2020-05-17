package com.lucasilva.pedidoapp.services.exceptions;

public class PedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PedidoNotFoundException(String msg) {
		super(msg);
	}
	
	public PedidoNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
