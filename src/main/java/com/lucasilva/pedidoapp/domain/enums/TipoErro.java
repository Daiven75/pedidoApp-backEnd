package com.lucasilva.pedidoapp.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoErro {

	VALIDATION_ERRORS("PED-0000", "Erros de validação"),
	CATEGORIA_NAO_ENCONTRADA("PED-0001", "Categoria não encontrada!"),
	CATEGORIA_COM_PRODUTOS("PED-0002", "Não é possível excluir categoria que possua produtos!"),
	EMAIL_NAO_ENCONTRADO("PED-0003", "Email não encontrado!"),
	CLIENTE_NAO_ENCONTRADO("PED-0004", "Cliente não encontrado!"), 
	ACESSO_NEGADO("PED-0005", "Acesso negado."), 
	CLIENTE_COM_PEDIDOS("PED-0006", "Não é possuir excluir o cliente pois o mesmo possui pedidos!"),
	ESTADO_NAO_ENCONTRADO("PED-0007", "Estado não encontrado!"),
	APENAS_PNG_E_JPG("PED-0008", "Somente imagens PNG e o JPG são permitidas"), 
	ERRO_LER_ARQUIVO("PED-0009", "Erro ao ler arquivo"),
	PEDIDO_NAO_ENCONTRADO("PED-0010", "Pedido não encontrado!"),
	PRODUTO_NAO_ENCONTRADO("PED-0011", "Produto não encontrado!"),
	CONVERTER_URL_PARA_URI("PED-0012", "Erro ao converter URL para URI"),
	ERRO_IO("PED-0013", "Erro de IO"),
	ERRO_CONSULTAR_CEP("PED-0014", "Erro ao consultar cep");

	private final String codigo;
	private final String descricao;
	
	public String toString() {
		return this.codigo + " - " + this.descricao;
	}
}