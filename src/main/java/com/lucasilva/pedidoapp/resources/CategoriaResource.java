package com.lucasilva.pedidoapp.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Categoria categoria = categoriaService.buscar(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping()
	public ResponseEntity<Void> cadastraCategoria(@RequestBody Categoria categoria) {
		categoria = categoriaService.cadastraCategoria(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
}
