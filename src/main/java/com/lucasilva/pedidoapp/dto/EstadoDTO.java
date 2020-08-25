package com.lucasilva.pedidoapp.dto;

import com.lucasilva.pedidoapp.domain.Estado;

public class EstadoDTO {

	private Long id;
	private String nome;
	
	public EstadoDTO() {
	}
	
	public EstadoDTO(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
