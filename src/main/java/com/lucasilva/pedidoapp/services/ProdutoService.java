package com.lucasilva.pedidoapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.domain.enums.TipoErro;
import com.lucasilva.pedidoapp.repositories.CategoriaRepository;
import com.lucasilva.pedidoapp.repositories.ProdutoRepository;
import com.lucasilva.pedidoapp.services.exceptions.PedidoNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscaPorId(Long id) {
		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		return optionalProduto.orElseThrow(
				() -> new PedidoNotFoundException(
						TipoErro.PRODUTO_NAO_ENCONTRADO.toString()));
	}
	
	public Page<Produto> buscaPagina(
			String nome,
			List<Long> ids,
			Integer pagina, 
			Integer linhasPorPagina, 
			String ordenaPor, 
			String direcao) {
		
			PageRequest paginaRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenaPor);
			List<Categoria> listcategorias = categoriaRepository.findAllById(ids); 
			return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(
					nome, 
					listcategorias, 
					paginaRequest);
	}
}