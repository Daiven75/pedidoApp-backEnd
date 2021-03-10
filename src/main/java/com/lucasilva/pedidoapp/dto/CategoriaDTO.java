package com.lucasilva.pedidoapp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty; 

import org.hibernate.validator.constraints.Length;

import com.lucasilva.pedidoapp.domain.Categoria;

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 4, max = 50, message = "Tamanho deve conter entre 4 a 50 caracteres")
	private String nome;
	
	public CategoriaDTO() {
	}
	
	public CategoriaDTO(String nome) {
		this.nome = nome;
	}
	
	public CategoriaDTO(Categoria categoria) {
		this.nome = categoria.getNome();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
