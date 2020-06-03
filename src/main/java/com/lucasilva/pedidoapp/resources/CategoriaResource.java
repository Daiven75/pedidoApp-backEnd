package com.lucasilva.pedidoapp.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.dto.CategoriaDTO;
import com.lucasilva.pedidoapp.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> buscaPorId(@PathVariable Long id) {
		Categoria categoria = categoriaService.buscaPorId(id);
		return ResponseEntity.ok().body(categoria);
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')") 
	@PostMapping()
	public ResponseEntity<Void> cadastraCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria = categoriaService.cadastraCategoria(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')") 
	@PutMapping(value = "/{id}")
	public ResponseEntity<Categoria> atualizaCategoria(
			@PathVariable Long id,
			@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = categoriaService.atualizaCategoria(categoria);
		return ResponseEntity.noContent().build();
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletaCategoria(@PathVariable Long id) {
		categoriaService.deletaCategoria(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> buscarTodos() {
		List<Categoria> listaCategoria = categoriaService.buscaTodos();
		List<CategoriaDTO> listaCategoriaDTO = listaCategoria.stream()
				.map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listaCategoriaDTO);
	}
	
	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> buscaPagina(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina,
			@RequestParam(value="ordenaPor", defaultValue="nome") String ordenaPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		Page<Categoria> pageCategoria = categoriaService.buscaPagina(pagina, linhasPorPagina, ordenaPor, direcao);
		Page<CategoriaDTO> pageCategoriaDTO = pageCategoria.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(pageCategoriaDTO);
	}
}
