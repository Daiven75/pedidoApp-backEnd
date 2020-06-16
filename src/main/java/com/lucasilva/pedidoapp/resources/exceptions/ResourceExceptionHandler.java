package com.lucasilva.pedidoapp.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.lucasilva.pedidoapp.services.exceptions.AuthorizationException;
import com.lucasilva.pedidoapp.services.exceptions.CategoriaNotFoundException;
import com.lucasilva.pedidoapp.services.exceptions.ClienteNotFoundException;
import com.lucasilva.pedidoapp.services.exceptions.DataIntegrityException;
import com.lucasilva.pedidoapp.services.exceptions.FileException;
import com.lucasilva.pedidoapp.services.exceptions.PedidoNotFoundException;

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
	
	@ExceptionHandler(PedidoNotFoundException.class)
	public ResponseEntity<StandardError> categoriaNotFound(
			PedidoNotFoundException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> categoriaNotFound(
			DataIntegrityException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(
			MethodArgumentNotValidException e,
			HttpServletRequest request) {
		
		ValidationError err = new ValidationError(
				HttpStatus.BAD_REQUEST.value(), 
				"Erro de validação", 
				System.currentTimeMillis());
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(
			AuthorizationException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.FORBIDDEN.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(
			FileException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(
			AmazonServiceException e,
			HttpServletRequest request) {
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		StandardError err = new StandardError(
				code.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(code).body(err);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonCliente(
			AmazonClientException e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(
			AmazonS3Exception e,
			HttpServletRequest request) {
		
		StandardError err = new StandardError(
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
