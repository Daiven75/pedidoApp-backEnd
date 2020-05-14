package com.lucasilva.pedidoapp.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lucasilva.pedidoapp.services.exceptions.CategoriaNotFoundException;
import com.lucasilva.pedidoapp.services.exceptions.ClienteNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(CategoriaNotFoundException.class)
	public ResponseEntity<StandardError> categoriaNotFound(
			CategoriaNotFoundException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<StandardError> categoriaNotFound(
			ClienteNotFoundException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
