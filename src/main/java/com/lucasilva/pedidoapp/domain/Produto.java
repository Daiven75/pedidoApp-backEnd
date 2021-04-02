package com.lucasilva.pedidoapp.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucasilva.pedidoapp.dto.ProdutoSaveDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double preco;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "PRODUTO_CATEGORIA",
			joinColumns = @JoinColumn(name = "produto_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto(Long id, String nome, Double preco) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Produto(ProdutoSaveDTO produtoSaveDTO) {
		this.nome = produtoSaveDTO.getNome();
		this.preco = produtoSaveDTO.getPreco();
	}
	
	@JsonIgnore
	public List<Pedido> getPedidos() {
		List<Pedido> listaPedidos = new ArrayList<>();
			for(ItemPedido x : itens) {
				listaPedidos.add(x.getPedido());
			}
		return listaPedidos;
	}
}