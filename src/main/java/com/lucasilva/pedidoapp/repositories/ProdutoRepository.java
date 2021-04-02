package com.lucasilva.pedidoapp.repositories;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(
			String nome, 
			List<Categoria> listCategorias, 
			Pageable pageRequest);
}