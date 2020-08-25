package com.lucasilva.pedidoapp.dto;

import com.lucasilva.pedidoapp.domain.Cidade;

public class CidadeDTO {

	private Long id;
	private String nome;
	
	public CidadeDTO() {
	}
	
	public CidadeDTO(Cidade cidade) {
		this.id = cidade.getId();
		this.nome = cidade.getNome();
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
