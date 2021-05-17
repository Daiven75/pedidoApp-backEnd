package com.lucasilva.pedidoapp.resources;

import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.dto.ProdutoDTO;
import com.lucasilva.pedidoapp.dto.ProdutoSaveDTO;
import com.lucasilva.pedidoapp.services.ProdutoService;
import com.lucasilva.pedidoapp.utils.URIUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;

	@ApiOperation(value = "Busca todos os produtos com paginação")
	@GetMapping()
	public ResponseEntity<Page<ProdutoDTO>> buscaPagina(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value="pagina", defaultValue="0") Integer pagina, 
			@RequestParam(value="linhasPorPagina", defaultValue="24") Integer linhasPorPagina,
			@RequestParam(value="ordenaPor", defaultValue="nome") String ordenaPor, 
			@RequestParam(value="direcao", defaultValue="ASC") String direcao) {
		
		String nomeDecoded = URIUtils.decodeParam(nome);
		List<Long> listCategorias = URIUtils.decodeLongList(categorias);
		
		Page<Produto> pageProduto = produtoService.buscaPagina(
				nomeDecoded,
				listCategorias,
				pagina, 
				linhasPorPagina,
				ordenaPor,
				direcao);
		Page<ProdutoDTO> pageProdutoDTO = pageProduto.map(ProdutoDTO::new);
		
		return ResponseEntity.ok().body(pageProdutoDTO);
	}

	@PostMapping
	@CacheEvict(value = "categorias", allEntries = true)
	public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Valid ProdutoSaveDTO produtoSaveDTO) {
		var produto = produtoService.cadastrarProduto(
			new Produto(produtoSaveDTO), produtoSaveDTO.getCategorias());
		var uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}