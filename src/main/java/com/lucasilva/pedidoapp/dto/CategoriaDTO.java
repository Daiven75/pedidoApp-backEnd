package com.lucasilva.pedidoapp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty; 

import org.hibernate.validator.constraints.Length;

import com.lucasilva.pedidoapp.domain.Categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 4, max = 50, message = "Tamanho deve conter entre 4 a 50 caracteres")
	private String nome;
	
	public CategoriaDTO(Categoria categoria) {
		this.nome = categoria.getNome();
	}
}
