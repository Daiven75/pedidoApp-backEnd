package com.lucasilva.pedidoapp.resources;

import com.lucasilva.pedidoapp.domain.Categoria;
import com.lucasilva.pedidoapp.dto.CategoriaDTO;
import com.lucasilva.pedidoapp.services.CategoriaService;
import com.lucasilva.pedidoapp.utils.RequestUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private RequestUtils requestUtils;

	@ApiOperation(value = "Busca categoria por id")
	@Cacheable(value = "categorias", key = "#id")
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> buscaPorId(@PathVariable Long id) {
		var categoria = categoriaService.buscaPorId(id);
		return ResponseEntity.ok().body(categoria);
	}

    @PreAuthorize("hasAnyRole('ADMIN')") 
    @ApiOperation(value = "Cadastra uma categoria")
    @CacheEvict(value = "categorias", allEntries = true)
    @PostMapping()
	public ResponseEntity<Void> cadastraCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
    	var categoria = categoriaService.cadastraCategoria(new Categoria(categoriaDTO));
		var uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build(); // retornando a URI criada a partir do insert 
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')") 
    @ApiOperation(value = "Atualiza uma categoria")
    @CacheEvict(value = "categorias", allEntries = true)
	@PutMapping(value = "/{id}")
	public ResponseEntity<Categoria> atualizaCategoria(
			@PathVariable Long id,
			@Valid @RequestBody CategoriaDTO categoriaDTO) {
		categoriaService.atualizaCategoria(id, categoriaDTO);
    	return ResponseEntity.noContent().build();
	}
	
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "Remove uma categoria")
    @ApiResponses(value = {
    		@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
    		@ApiResponse(code = 404, message = "Código inexistente") })
    @CacheEvict(value = "categorias", allEntries = true)
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletaCategoria(@PathVariable Long id) {
		categoriaService.deletaCategoria(id);
		return ResponseEntity.noContent().build();
	}
	
    @ApiOperation(value = "Busca todas as categorias")
    @Cacheable(value = "categorias")
	@GetMapping
	public ResponseEntity<List<Categoria>> buscarTodos() {
		return ResponseEntity.ok().body(categoriaService.buscaTodos());
	}
	
    @ApiOperation(value = "Busca todas as categorias com paginação")
    @Cacheable(value = "categorias")
	@GetMapping(value="/page")
	public ResponseEntity<Page<Categoria>> buscaPagina(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina,
			@RequestParam(value="ordenaPor", defaultValue="nome") String ordenaPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		Page<Categoria> pageCategoria = categoriaService.buscaPagina(pagina, linhasPorPagina, ordenaPor, direcao);
		return ResponseEntity.ok().body(pageCategoria);
	}
}