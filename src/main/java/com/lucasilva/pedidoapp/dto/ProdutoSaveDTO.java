package com.lucasilva.pedidoapp.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProdutoSaveDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;

	@NotNull(message = "Preenchimento obrigatório")
	private Double preco;
	
    @NotEmpty(message = "Preenchimento obrigatório")
	private List<Long> categorias;
}