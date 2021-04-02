package com.lucasilva.pedidoapp.dto;

import java.io.Serializable;

import com.lucasilva.pedidoapp.domain.Produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Double preco;
	
	public ProdutoDTO(Produto produto) {
		nome = produto.getNome();
		preco = produto.getPreco();
	}
}