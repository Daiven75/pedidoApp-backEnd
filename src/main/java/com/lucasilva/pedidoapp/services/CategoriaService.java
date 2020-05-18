package com.lucasilva.pedidoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.repositories.CategoriaRepository;
import com.lucasilva.pedidoapp.services.exceptions.CategoriaNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria buscaPorId(Long id) {
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
		return optionalCategoria
				.orElseThrow(() -> new CategoriaNotFoundException(
						"Categoria não encontrada! "
						+ "Id: " + id + 
						", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria cadastraCategoria(Categoria categoria) {
		categoria.setId(null); // garantido da requisicao seja nulo, para nao realizar update
		return categoriaRepository.save(categoria);
	}

	public Categoria atualizaCategoria(Categoria categoria) {
		buscaPorId(categoria.getId());
		return categoriaRepository.save(categoria);
	}
}
