package com.lucasilva.pedidoapp.resources;

import java.util.List;

import javax.validation.Valid;

import com.lucasilva.pedidoapp.domain.Produto;
import com.lucasilva.pedidoapp.dto.ProdutoDTO;
import com.lucasilva.pedidoapp.dto.ProdutoSaveDTO;
import com.lucasilva.pedidoapp.resources.utils.URI;
import com.lucasilva.pedidoapp.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;

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
		
		String nomeDecoded = URI.decodeParam(nome);
		List<Long> listCategorias = URI.decodeLongList(categorias);
		
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
		Produto produto = produtoService.cadastrarProduto(
			new Produto(produtoSaveDTO), produtoSaveDTO.getCategorias());
		java.net.URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}