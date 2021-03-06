package com.lucasilva.pedidoapp.services;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.domain.enums.TipoErro;
import com.lucasilva.pedidoapp.dto.CategoriaDTO;
import com.lucasilva.pedidoapp.repositories.CategoriaRepository;
import com.lucasilva.pedidoapp.services.exceptions.CategoriaNotFoundException;
import com.lucasilva.pedidoapp.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscaPorId(Long id) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
		return categoriaOptional
				.orElseThrow(() -> new CategoriaNotFoundException(
						TipoErro.CATEGORIA_NAO_ENCONTRADA.toString()));
	}
	
	public Categoria cadastraCategoria(Categoria categoria) {
		categoria.setId(null); // garantido da requisicao seja nulo, para nao realizar update
		return categoriaRepository.save(categoria);
	}

	public void atualizaCategoria(Long id, CategoriaDTO categoriaDTO) {
		var categoria = buscaPorId(id);
		atualizaDados(categoria, categoriaDTO);
		categoriaRepository.save(categoria);
	}

	public void deletaCategoria(Long id) {
		buscaPorId(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(TipoErro.CATEGORIA_COM_PRODUTOS.toString());
		}
	}

	public List<Categoria> buscaTodos() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> buscaPagina(Integer pagina, Integer linhasPorPagina, String ordenaPor, String direcao) {
		var paginaRequest = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(direcao), ordenaPor);
		return categoriaRepository.findAll(paginaRequest);
	}
	
	private void atualizaDados(Categoria categoria, CategoriaDTO atualizaCategoria) {
		categoria.setNome(atualizaCategoria.getNome());
	}
}